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

    @GetMapping
    public Response findExam(String examineeId) {
        logger.info(examineeId);
        if ("".equals(examineeId)||examineeId==null) {
            return Response.error(USER_ERROR);
        }
        Iterator<ExamExaminationEntity> iterator = examinationDao.findAll().iterator();
        while (iterator.hasNext()) {
            logger.info(iterator.next().getExaminationId());
        }
        return Response.ok(examinationDao.findAll().iterator());
    }
}
