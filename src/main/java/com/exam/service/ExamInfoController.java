package com.exam.service;

import com.exam.common.Response;
import com.exam.common.dao.ExaminationDao;
import com.exam.common.entity.ExamExaminationEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

import static com.exam.common.ErrorCode.USER_ERROR;

/**
 * Created by LX on 2017/7/21.
 */
@RestController
@RequestMapping("/examinfo1")
public class ExamInfoController {
    @Autowired
    ExaminationDao examinationDao;
    private Logger logger = LoggerFactory.getLogger(ExamInfoController.class);

    /**
     * 获取当前可参加所有考试
     * @param examineeId
     * @return
     */
    @GetMapping
    public Response findExams(String examineeId) {
        logger.info("findExam"+examineeId);
        if ("".equals(examineeId)||examineeId==null) {
            return Response.error(USER_ERROR);
        }
        Iterator<ExamExaminationEntity> iterator = examinationDao.findAll().iterator();
        while (iterator.hasNext()) {
            logger.info(iterator.next().getExaminationId());
        }
        return Response.ok(examinationDao.findAll().iterator());
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
}
