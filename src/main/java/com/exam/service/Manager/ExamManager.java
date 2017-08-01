package com.exam.service.Manager;

import com.exam.common.Response;
import com.exam.common.dao.ExaminationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by LX on 2017/7/31.
 * 考试管理
 */
@RestController
@RequestMapping("/examManager")
public class ExamManager {

    @Autowired
    ExaminationDao examinationDao;

    private Logger logger = LoggerFactory.getLogger(ExamManager.class);

    @GetMapping
    public Response create(@CookieValue(value = "token", defaultValue = "hello") String token,
                           @CookieValue(value = "userId", defaultValue = "hello") String uid){
        //创建考试
        System.out.println(token);
        System.out.println(uid);
        return Response.ok(examinationDao.findAll());
    }

    @PostMapping("/handle")
    public Response examHandle(@CookieValue(value = "token", defaultValue = "") String token,
                               @CookieValue(value = "userId", defaultValue = "") String uid,
                               @RequestParam(defaultValue = "") String oper,
                               @RequestParam(defaultValue = "") String id,
                               @RequestParam(defaultValue = "") String examinationId,
                               @RequestParam(defaultValue = "") String examinationName,
                               @RequestParam(defaultValue = "") String answerTime,
                               @RequestParam(defaultValue = "") String examinationType,
                               @RequestParam(defaultValue = "") String questionCount,
                               @RequestParam(defaultValue = "") String examinationScoreAll,
                               @RequestParam(defaultValue = "") String examinationStart,
                               @RequestParam(defaultValue = "") String examinationEnd,
                               @RequestParam(defaultValue = "") String examinationInfo,
                               @RequestParam(defaultValue = "") String isDEL){
        logger.info("id-"+id);
        logger.info("examinationId-"+examinationId);
        logger.info(examinationName);
        logger.info("ANSWERTIME"+answerTime);
        logger.info(examinationType);
        logger.info(questionCount);
        logger.info(examinationScoreAll);
        logger.info(examinationStart+"");
        logger.info(examinationEnd+"");
        logger.info(examinationInfo+"");
        logger.info(oper);
        logger.info("del:"+isDEL);
        return Response.ok();
    }
}
