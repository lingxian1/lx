package com.exam.common.dao;

import com.exam.common.entity.ExamGradeEntity;
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
    //获取分数及状态
    public ExamGradeEntity findGrade(String examineeId,String examinationId){
        Map<String,String> str =new HashMap<>();
        str.put("examineeId",examineeId);
        str.put("examinationId",examinationId);
        return findByIds(str); //可能为NULL
    }
}
