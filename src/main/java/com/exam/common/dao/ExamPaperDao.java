package com.exam.common.dao;

import com.exam.common.entity.ExamExaminationPaperEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LX on 2017/7/24.
 */
@Component
@Repository
public class ExamPaperDao extends AbstractDao<ExamExaminationPaperEntity>{
    /**
     * 根据考试号寻找题目
     * @param examinationId
     * @return
     */
    public List<ExamExaminationPaperEntity> findByexam(String examinationId){
        return findBy("examinationId",examinationId);
    }

    /**
     * 获得某道题分值及正确率
     * @param examintionId
     * @param questionId
     * @return
     */
    public ExamExaminationPaperEntity findScore(String examintionId,String questionId){
        Map<String,String> str =new HashMap<>();
        str.put("examinationId",examintionId);
        str.put("questionId",questionId);
        return findByIds(str);
    }
}
