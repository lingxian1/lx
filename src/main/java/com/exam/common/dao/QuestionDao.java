package com.exam.common.dao;

import com.exam.common.entity.ExamQuestionEntity;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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
     * 根据字段获取问题
     * @param str
     * @param info
     * @return
     */
    public List<ExamQuestionEntity> findQuestionClass(String str,String info){
        List<ExamQuestionEntity> entities=findBy(str,info,true);
        if(entities.size()==0){
            return null;
        }
        return entities;
    }

    /**
     * 新的Id
     * @return
     */
    public String newQuestionId() {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery l = session.createSQLQuery("SELECT MAX(question_ID) FROM exam_question");
        String id = (String) l.list().get(0);
        Integer idd = Integer.valueOf(id);
        String newid = String.valueOf(idd + 1);
        return newid;
    }

    /**
     * 删除问题（保证历史记录完整性，设置问题状态01来实现）
     * @param questionId
     * @return
     */
    public boolean deleteById(String questionId){
        ExamQuestionEntity entity=findById(questionId);
        if(entity==null){
            return false;
        }
        entity.setIsDel("01");
        update(entity);
        return true;
    }
    /**
     * 修改问题
     * @param questionId
     * @param questionText
     * @param questionType
     * @param questionChooseCount
     * @param questionChooseA
     * @param questionChooseB
     * @param questionChooseC
     * @param questionChooseD
     * @param questionAnswer
     * @param questionClassification
     * @param questionOther
     * @return
     */
    public boolean updateById(String questionId,String questionText,String questionType,int questionChooseCount,
                              String questionChooseA,String questionChooseB,String questionChooseC,String questionChooseD,
                              String questionAnswer,String questionClassification,String questionOther){
        ExamQuestionEntity entity=findById(questionId);
        if(entity==null){
            return false;
        }
        entity.setQuestionText(questionText);
        entity.setQuestionType(questionType);
        entity.setQuestionChooseCount(questionChooseCount);
        entity.setQuestionChooseA(questionChooseA);
        entity.setQuestionChooseB(questionChooseB);
        entity.setQuestionChooseC(questionChooseC);
        entity.setQuestionChooseD(questionChooseD);
        entity.setQuestionAnswer(questionAnswer);
        entity.setQuestionClassification(questionClassification);
        entity.setQuestionCreateTime(new Timestamp(System.currentTimeMillis()));
//        entity.setQuestionOther(questionOther);
        update(entity);
        return true;
    }
}
