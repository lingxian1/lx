package com.exam.common.dao;

import com.exam.common.entity.ExamGradeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LX on 2017/7/24.
 * 考试成绩状态管理
 */
@Component
@Repository
public class GradeDao extends AbstractDao<ExamGradeEntity>{
    private Logger logger = LoggerFactory.getLogger(GradeDao.class);

    /**
     * 根据考试号获取所有成绩
     * @param examinationId
     * @return
     */
    public List<ExamGradeEntity> findGradeForExam(String examinationId){
        List<ExamGradeEntity> entities=findBy("examinationId",examinationId,false);
        if (entities==null||entities.size()==0) {
            return new ArrayList<>();
        }
        return entities;
    }
    /**
     * 获取个人分数及状态
     * @param examineeId
     * @param examinationId
     * @return
     */
    public ExamGradeEntity findGrade(String examineeId,String examinationId){
        Map<String,String> str =new HashMap<>();
        str.put("examineeId",examineeId);
        str.put("examinationId",examinationId);
        ExamGradeEntity grade=findByIds(str);
        //空值处理
        if(grade==null){
            grade=new ExamGradeEntity();
            grade.setExaminationState("is null");
            logger.info("grade state is null,you can exam");
        }
        return grade;
    }

    /**
     * 获取个人所有考试成绩
     * @param examineeId
     * @return
     */
    public List<ExamGradeEntity> findGrades(String examineeId){
        return findBy("examineeId",examineeId);
    }
}
