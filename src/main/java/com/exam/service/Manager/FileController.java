package com.exam.service.Manager;

import com.exam.common.ErrorCode;
import com.exam.common.Response;
import com.exam.common.dao.QuestionDao;
import com.exam.common.entity.ExamQuestionEntity;
import com.exam.common.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by LX on 2018/7/4.
 */
@RestController
@RequestMapping("/fileManager")
public class FileController {
    @Autowired
    QuestionDao questionDao;

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
            System.out.println(question.toString());
            questionDao.save(question);
        }
        return Response.ok();
    }
}
