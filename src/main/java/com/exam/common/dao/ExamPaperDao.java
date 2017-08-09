package com.exam.common.dao;

import com.exam.common.entity.ExamExaminationPaperEntity;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LX on 2017/7/24.
 * 每场考试的试题管理
 */
@Component
@Repository
public class ExamPaperDao extends AbstractDao<ExamExaminationPaperEntity>{
    /**
     * 根据考试号寻找题目 like--false
     * @param examinationId
     * @return
     */
    public List<ExamExaminationPaperEntity> findByexam(String examinationId){
        return findBy("examinationId",examinationId,false);
    }

    /**
     * 保存正确率
     * @param examinationId
     * @param questionId
     * @param accuracy
     */
    public void saveAccuracy(String examinationId,String questionId,double accuracy){
        ExamExaminationPaperEntity entity=findScore(examinationId,questionId);
        if(entity!=null){
            entity.setAccuracy(accuracy);
            save(entity);
        }else{
            logger.info("saveAccuracy is null");
        }
    }
    /**
     * 某场考试已绑定题目数量
     * @param examinationId
     * @return
     */
    public int findByexamCount(String examinationId){
        int count=0;
        List<ExamExaminationPaperEntity> entities=findByexam(examinationId);
        if(entities!=null){
            count=entities.size();
        }
        return count;
    }

    /**
     * 获得某道题分值及正确率
     * @param examinationId
     * @param questionId
     * @return
     */
    public ExamExaminationPaperEntity findScore(String examinationId,String questionId){
        Map<String,String> str =new HashMap<>();
        str.put("examinationId",examinationId);
        str.put("questionId",questionId);
        ExamExaminationPaperEntity entity=findByIds(str);
        if(entity==null){
           logger.info("findScore:entity is null");
        }
        return entity;
    }

    /**
     * 某场考试当前绑定试题总分
     * @param examintionId
     * @return
     */
    public int sumScore(String examintionId){
        int sum=0;
        Session session = sessionFactory.getCurrentSession();
        StringBuilder builder = new StringBuilder();
        String sql = builder.append("SELECT SUM(score) FROM exam_examination_paper WHERE examination_ID='")
                .append(examintionId)
                .append("' ")
                .toString();
        SQLQuery l = session.createSQLQuery(sql);
        if(l.list().get(0)!=null){
            sum= new Integer(l.list().get(0).toString());
        }
        return sum;
    }

    /**
     * 根据考试号删除所有绑定试题
     * @param examinationId
     */
    public void deleteAllQuestion(String examinationId){
        List<ExamExaminationPaperEntity> list =findBy("examinationId",examinationId);
        for(ExamExaminationPaperEntity entity:list){
            delete(entity);
        }
    }
}
