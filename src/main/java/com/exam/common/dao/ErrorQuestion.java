package com.exam.common.dao;

import com.exam.common.entity.ExamErrorQuestionEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 错题管理
 * Created by LX on 2017/8/3.
 */
@Component
@Repository
public class ErrorQuestion extends AbstractDao<ExamErrorQuestionEntity>{
    //todo 错题TOP10
    public List<ExamErrorQuestionEntity> findErrorQuestion(String top){
        return null;
    }
}
