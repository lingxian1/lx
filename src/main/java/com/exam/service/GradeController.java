package com.exam.service;

import com.exam.common.Response;
import com.exam.common.dao.ExaminationDao;
import com.exam.common.dao.GradeDao;
import com.exam.common.entity.ExamExaminationEntity;
import com.exam.common.entity.ExamGradeEntity;
import com.exam.common.other.GradeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.exam.common.ErrorCode.USER_ERROR;

/**
 * Created by LX on 2017/7/25.
 */
@RestController
@RequestMapping("/grade")
public class GradeController {
    @Autowired
    ExaminationDao examinationDao;
    @Autowired
    GradeDao gradeDao;

    private Logger logger = LoggerFactory.getLogger(ExamInfoController.class);

    @GetMapping
    public Response findGrades(String examineeId) {
        logger.info("findGrades"+examineeId);
        if ("".equals(examineeId)||examineeId==null) {
            return Response.error(USER_ERROR);
        }
        List<ExamGradeEntity>grades=gradeDao.findGrades(examineeId);
        if (grades.size() == 0) {
            return null;
        }
        List<GradeMessage> messages=new ArrayList<>();
        Iterator<ExamGradeEntity> iterator = grades.iterator();
        while (iterator.hasNext()) {
            ExamGradeEntity examGradeEntity=iterator.next();
            ExamExaminationEntity examinationEntity=examinationDao.findById(examGradeEntity.getExaminationId());
            GradeMessage gradeMessage=new GradeMessage();
            gradeMessage.setExaminationId(examGradeEntity.getExaminationId());
            gradeMessage.setExaminationState(examGradeEntity.getExaminationState());
            gradeMessage.setGrade(examGradeEntity.getGrade());
            gradeMessage.setExamineeId(examineeId);
            gradeMessage.setExaminationName(examinationEntity.getExaminationName());
            gradeMessage.setExaminationEnd(examinationEntity.getExaminationEnd());
            gradeMessage.setExaminationScoreAll(examinationEntity.getExaminationScoreAll());
            messages.add(gradeMessage);
        }

        return Response.ok(messages);
    }
}
