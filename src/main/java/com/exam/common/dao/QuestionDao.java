package com.exam.common.dao;

import com.exam.common.entity.ExamQuestionEntity;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import static com.exam.common.constant.Constant.ONE_DAY;

/**
 * Created by LX on 2017/7/24.
 * 问题管理
 */
@Component
@Repository
public class QuestionDao extends AbstractDao<ExamQuestionEntity> {
    /**
     * 根据Id获得问题详情
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
        Iterator<ExamQuestionEntity> iterator=entities.iterator();
        while (iterator.hasNext()){
            ExamQuestionEntity entity=iterator.next();
            if(entity.getIsDel().equals("01")){
                iterator.remove(); //去掉已删除的
            }
        }
        return entities;
    }

    /**
     * 根据分类获取问题
     * @param info
     * @return
     */
    public List<ExamQuestionEntity> findQuestions(String info){
        List<ExamQuestionEntity> entities;
        if("".equals(info)){
            entities=findAll();
            Iterator<ExamQuestionEntity> iterator=entities.iterator();
            while (iterator.hasNext()){
                ExamQuestionEntity entity=iterator.next();
                if(entity.getIsDel().equals("01")){
                    iterator.remove(); //去掉已删除的
                }
            }
        }else{
            entities=findQuestionClass("questionClassification",info);
        }
        if(entities.size()==0){
            return null;
        }
        return entities;
    }

    /**
     * 根据分类，最近天数，类型获取问题
     * @param info
     * @param days
     * @param type
     * @return
     */
    public List<ExamQuestionEntity> findQuestions(String info,int days,String type){
        List<ExamQuestionEntity> entities=findQuestions(info);
        if(entities.size()==0){
            return null;
        }
        Long time=0L;
        if(days!=0){
            time=new Timestamp(System.currentTimeMillis()).getTime()-days*ONE_DAY;
        }

        Iterator<ExamQuestionEntity> iterator=entities.iterator();
        if(type.equals("all")){
            while (iterator.hasNext()){
                ExamQuestionEntity entity=iterator.next();
                if(entity.getQuestionCreateTime().getTime()<time){
                    iterator.remove();
                }
            }
        }else{
            while (iterator.hasNext()){
                ExamQuestionEntity entity=iterator.next();
                if(entity.getQuestionCreateTime().getTime()<time||(!entity.getQuestionType().equals(type))){
                    iterator.remove();
                }
            }
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
        if(l.list().get(0)==null){
            return "1000000000";
        }else{
            String id = (String) l.list().get(0);
            Integer idd = Integer.valueOf(id);
            String newid = String.valueOf(idd + 1);
            return newid;
        }
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
