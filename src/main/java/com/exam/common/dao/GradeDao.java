package com.exam.common.dao;

import com.exam.common.entity.ExamGradeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LX on 2017/7/24.
 */
@Component
@Repository
public class GradeDao extends AbstractDao<ExamGradeEntity>{
    private Logger logger = LoggerFactory.getLogger(GradeDao.class);
    /**
     * 获取分数及状态
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
}
