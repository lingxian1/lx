package com.exam.common.dao;

import com.exam.common.entity.ExamAnswerLogEntity;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        String sql = builder.append("SELECT SUM(score_real) FROM exam_answer_log where examinee_ID='")
                .append(examineeId)
                .append("' and examination_ID='")
                .append(examinationId)
                .append("'")
                .toString();
        SQLQuery l = session.createSQLQuery(sql);
        List list = l.list();
        return new Integer(list.get(0).toString());
    }
}
