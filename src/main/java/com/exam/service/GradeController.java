package com.exam.service;

import com.exam.common.Response;
import com.exam.common.dao.ExaminationDao;
import com.exam.common.dao.GradeDao;
import com.exam.common.entity.ExamExaminationEntity;
import com.exam.common.entity.ExamGradeEntity;
import com.exam.common.other.GradeMessage;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.comparators.ComparatorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.exam.common.ErrorCode.EXAM_GRADE_NULL;
import static com.exam.common.ErrorCode.USER_ERROR;

/**
 * Created by LX on 2017/7/25.
 * 获取考试成绩
 */
@RestController
@RequestMapping("/getgrade")
public class GradeController {
    @Autowired
    ExaminationDao examinationDao;
    @Autowired
    GradeDao gradeDao;

    private Logger logger = LoggerFactory.getLogger(ExamInfoController.class);

    /**
     * 获取个人成绩
     * @param examineeId
     * @return
     */
    @GetMapping
    public Response findGrades(String examineeId) {
        logger.info("findGrades"+examineeId);
        if ("".equals(examineeId)||examineeId==null) {
            return Response.error(USER_ERROR);
        }
        List<ExamGradeEntity>grades=gradeDao.findGrades(examineeId);
        if (grades.size() == 0) {
            return Response.error(EXAM_GRADE_NULL);
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
            gradeMessage.setExaminationEnd(examGradeEntity.getExaminationTime());
            gradeMessage.setExaminationScoreAll(examinationEntity.getExaminationScoreAll());
            messages.add(gradeMessage);
        }
        Comparator mycmp2 = ComparableComparator.getInstance();
        mycmp2 = ComparatorUtils.nullHighComparator(mycmp2); //允许null
        mycmp2=ComparatorUtils.reversedComparator(mycmp2);//逆序
        // 声明要排序的对象的属性，并指明所使用的排序规则，如果不指明，则用默认排序
        ArrayList<Object> sortFields = new ArrayList<Object>();
        //结束时间降序
        sortFields.add(new BeanComparator("examinationEnd", mycmp2));
        // 创建一个排序链
        ComparatorChain multiSort = new ComparatorChain(sortFields);
        // 开始真正的排序，按照先主，后副的规则
        Collections.sort(messages, multiSort);
        return Response.ok(messages);
    }
}
