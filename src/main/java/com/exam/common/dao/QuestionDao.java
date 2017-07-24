package com.exam.common.dao;

import com.exam.common.entity.ExamQuestionEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by LX on 2017/7/24.
 */
@Component
@Repository
public class QuestionDao extends AbstractDao<ExamQuestionEntity> {
    public ExamQuestionEntity findQuestion(String questionId){
        return findById(questionId);
    }
}
