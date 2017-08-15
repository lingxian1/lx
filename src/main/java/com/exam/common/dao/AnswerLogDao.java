package com.exam.common.dao;

import com.exam.common.entity.ExamAnswerLogEntity;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LX on 2017/7/25.
 * 用于操作考生提交的答案
 */
@Component
@Repository
public class AnswerLogDao extends AbstractDao<ExamAnswerLogEntity> {
    /**
     * 总分计算
     * @param examineeId
     * @param examinationId
     * @return
     */
    public int getGrade(String examineeId,String examinationId){
        Session session = sessionFactory.getCurrentSession();
        StringBuilder builder = new StringBuilder();
        String sql = builder.append("SELECT SUM(score_real) FROM exam_answer_log where examinee_ID= ? and examination_ID= ?")
                .toString();
        SQLQuery l = session.createSQLQuery(sql);
        l.setString(0,examineeId);
        l.setString(1,examinationId);
        List list = l.list();
        if(list==null||list.get(0)==null){
            return -1;
        }
        return new Integer(list.get(0).toString());
    }

    /**
     * 根据考试号查找试题及考生答案
     * @param examinationId
     * @return
     */
    public List<ExamAnswerLogEntity> findByExamination(String examinationId,String questionId){
        Map<String,String> str =new HashMap<>();
        str.put("examinationId",examinationId);
        str.put("questionId",questionId);
        List<ExamAnswerLogEntity> list = findBy(str,false);
        return list;
    }
}
