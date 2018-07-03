package com.exam.service;

import com.exam.common.Response;
import com.exam.common.dao.ExaminationDao;
import com.exam.common.dao.ExamineeDao;
import com.exam.common.dao.GradeDao;
import com.exam.common.entity.ExamExaminationEntity;
import com.exam.common.entity.ExamExamineeEntity;
import com.exam.common.util.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

import static com.exam.common.ErrorCode.*;

/**
 * Created by LX on 2017/7/21.
 * 考试信息
 */
@RestController
@RequestMapping("/examinfo1Examinees")
public class ExamInfoController {
    @Autowired
    ExaminationDao examinationDao;
    @Autowired
    GradeDao gradeDao;
    @Autowired
    ExamineeDao examineeDao;
    private Logger logger = LoggerFactory.getLogger(ExamInfoController.class);

    /**
     * 获取当前可参加所有考试
     * @param examineeId
     * @return
     */
    @GetMapping
    public Response findExams(String examineeId) {
//        logger.info("findExam"+examineeId);
        if ("".equals(examineeId)||examineeId==null) {
            return Response.error(USER_ERROR);
        }
        List<ExamExaminationEntity> exams=examinationDao.findUseful();
        if (exams.size() == 0) {
            return Response.error(NO_SUCH_EXAM);
        }
        //找出所有有效考试
        Iterator<ExamExaminationEntity> iterator = exams.iterator();
        //移除已完成考试
        while (iterator.hasNext()) {
            ExamExaminationEntity examinationEntity=iterator.next();
            String flag=gradeDao.findGrade(examineeId,examinationEntity.getExaminationId()).getExaminationState();
            if("00".equals(flag)){
                iterator.remove();
            }
        }
        return Response.ok(exams);
    }

    /**
     * 获取某场考试
     * @param examineeId
     * @param examinationId
     * @return
     */
    @GetMapping("/examinfo2")
    public Response findExam(String examineeId,String examinationId) {
        logger.info("findExam"+examineeId);
        logger.info("findExam"+examinationId);
        if ("".equals(examineeId)||examineeId==null) {
            return Response.error(USER_ERROR);
        }
        ExamExaminationEntity examExaminationEntity=examinationDao.findById(examinationId);
        return Response.ok(examExaminationEntity);
    }

    @PostMapping("/changePassword")
    public Response changePassword(@RequestParam(defaultValue = "") String old,
                                   @RequestParam(defaultValue = "") String new0,
                                   @CookieValue(value = "userId", defaultValue = "") String examineeId){
        ExamExamineeEntity examExamineeEntity=examineeDao.findByStr("examineeId",examineeId);
        if(examExamineeEntity==null){
            return Response.error(USER_EMPTY);
        }else if(examExamineeEntity.getIdentity()!=null && examExamineeEntity.getIdentity().equals("2")){
            return Response.error(USER_DELETE);
        }
        String temp=examExamineeEntity.getSalt()+old;
        if(examExamineeEntity.getPassword().equals(Md5Utils.stringMD5(temp))){
            String getsalt = Md5Utils.stringMD5(examineeId+System.currentTimeMillis());
            String newpass = Md5Utils.stringMD5(getsalt+new0);
            examExamineeEntity.setSalt(getsalt);
            examExamineeEntity.setPassword(newpass);
            examineeDao.update(examExamineeEntity);
            return Response.ok();
        }else{
            return Response.error(PASSWORD_ERROR);
        }
    }

    public static void main(String[] args) {
        System.out.println(Md5Utils.stringMD5("111111"));
    }
}
