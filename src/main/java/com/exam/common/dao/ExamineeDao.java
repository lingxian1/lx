package com.exam.common.dao;

import com.exam.common.entity.ExamExamineeEntity;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LX on 2017/7/20.
 */
@Component
@Repository
public class ExamineeDao extends AbstractDao<ExamExamineeEntity> {

    public ExamExamineeEntity findByID(String value){
        List<ExamExamineeEntity> examExamineeEntities = super.findBy("examinee_ID", value);
        if (examExamineeEntities.size() == 0) {
            return null;
        }
        return examExamineeEntities.get(0);

    }
    public String newUsersId() {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery l = session.createSQLQuery("SELECT MAX(examinee_ID) FROM exam_examinee");
        String id = (String) l.list().get(0);
        Integer idd = Integer.valueOf(id);
        String newid = String.valueOf(idd + 1);
        return newid;
    }
}
