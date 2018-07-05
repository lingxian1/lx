package com.exam.service.Manager;

import com.exam.common.ErrorCode;
import com.exam.common.Response;
import com.exam.common.dao.AreaDao;
import com.exam.common.dao.ExamineeDao;
import com.exam.common.dao.GradeDao;
import com.exam.common.dao.QuestionDao;
import com.exam.common.entity.ExamExamineeEntity;
import com.exam.common.entity.ExamGradeEntity;
import com.exam.common.entity.ExamQuestionEntity;
import com.exam.common.other.AllGrade;
import com.exam.common.util.ExcelUtil;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.comparators.ComparatorChain;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by LX on 2018/7/4.
 */
@RestController
@RequestMapping("/fileManager")
public class FileController {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    AreaDao areaDao;
    @Autowired
    GradeDao gradeDao;
    @Autowired
    ExamineeDao examineeDao;

    @PostMapping("/uploadQuestion")
    public Response uploadQuestion(@RequestParam("file") MultipartFile file) throws Exception{
        if(file.isEmpty()){
            return Response.error(ErrorCode.FILE_UPLOAD_FAIL);
        }
        String name=file.getOriginalFilename();
        if(!name.endsWith(".xls") && !name.endsWith(".xlsx")){
            return Response.error(ErrorCode.FILE_TYPE_ERROR);
        }
        List<ExamQuestionEntity> questions=(List<ExamQuestionEntity>) ExcelUtil.readExcel(file.getInputStream(),1,3, ExamQuestionEntity.class);
        for(ExamQuestionEntity question : questions){
            question.setQuestionId(questionDao.newQuestionId());
            question.setQuestionCreateTime(new Timestamp(System.currentTimeMillis()));
            question.setIsDel("00");
//            System.out.println(question.toString());
            questionDao.save(question);
        }
        return Response.ok();
    }

    /**
     * 某场考试所有成绩
     * @param examinationId
     * @return
     */
    @GetMapping("/getGrade/{examinationId}")
    public void gradeExport(@PathVariable("examinationId") String examinationId,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<AllGrade> list = new ArrayList<>();
        List<ExamGradeEntity> entities = gradeDao.findGradeForExam(examinationId);
        if(entities==null){
            return ;
        }
        //对成绩进行排序
        Comparator mycmp1 = ComparableComparator.getInstance();
        mycmp1 = ComparatorUtils.reversedComparator(mycmp1);//降序
        Comparator mycmp2 = ComparableComparator.getInstance();
        mycmp2 = ComparatorUtils.nullHighComparator(mycmp2); //允许null
        // 声明要排序的对象的属性，并指明所使用的排序规则，如果不指明，则用默认排序
        ArrayList<Object> sortFields = new ArrayList<Object>();
        sortFields.add(new BeanComparator("grade", mycmp1));
        sortFields.add(new BeanComparator("grade", mycmp2));
        // 创建一个排序链
        ComparatorChain multiSort = new ComparatorChain(sortFields);
        // 开始真正的排序，按照先主，后副的规则
        Collections.sort(entities, multiSort);

        //组装
        Iterator<ExamGradeEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            ExamGradeEntity examGradeEntity = iterator.next();
            String examineeId = examGradeEntity.getExamineeId();
            ExamExamineeEntity examineeEntity = examineeDao.findById(examineeId);
            AllGrade allGrade = new AllGrade();
            allGrade.setExamineeId(examineeId);
            allGrade.setGrade(examGradeEntity.getGrade());
            allGrade.setExaminationTime(examGradeEntity.getExaminationTime());
            allGrade.setPhone(examineeEntity.getPhone());
            allGrade.setName(examineeEntity.getName());
            allGrade.setAreaName(areaDao.findById(examineeEntity.getAreaId()).getAreaName());
            list.add(allGrade);
        }
        String excelName="grade";
        LinkedHashMap<String, String> fieldMap =new LinkedHashMap<String, String>() ;
        fieldMap.put("name", "姓名");
        fieldMap.put("phone", "手机号");
        fieldMap.put("areaName", "地区名");
        fieldMap.put("grade", "成绩");
        String s=ExcelUtil.exportExcel(excelName,list, fieldMap,"\\Users\\temp");
        File file = new File(s);
        if(!file.exists()){
            return ;
        }else {
            InputStream in = new FileInputStream(s);
            OutputStream out = response.getOutputStream();
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + excelName+".xls");   // 设置文件名
            //把输入流copy到输出流
            IOUtils.copy(in, out);
            out.flush();
        }
    }
}
