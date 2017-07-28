package com.exam.common.dao;

import com.exam.common.entity.ExamQuestionEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LX on 2017/7/24.
 * 问题管理
 */
@Component
@Repository
public class QuestionDao extends AbstractDao<ExamQuestionEntity> {
    /**
     * 根据问题获得问题详情
     * @param questionId
     * @return
     */
    public ExamQuestionEntity findQuestion(String questionId){
        return findById(questionId);
    }


    /**
     * 根据分类获取问题
     * @param questionClassification
     * @return
     */
    public List<ExamQuestionEntity> findQuestionClass(String questionClassification){
        List<ExamQuestionEntity> entities=findBy("questionClassification",questionClassification,true);
        if(entities.size()==0){
            return null;
        }
        return entities;
    }


}
