package com.exam.common.dao;

import com.exam.common.entity.ExamExamineeEntity;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LX on 2017/7/20.
 * 考生信息管理
 */
@Component
@Repository
public class ExamineeDao extends AbstractDao<ExamExamineeEntity> {

    /**
     * 关键字段查询
     * @param str
     * @param value
     * @return
     */
    public ExamExamineeEntity findByStr(String str,String value){
        List<ExamExamineeEntity> examExamineeEntities = super.findBy(str, value);
        if (examExamineeEntities.size() == 0) {
            return null;
        }
        return examExamineeEntities.get(0);
    }

    /**
     * 查找所有考生信息 但是不返回密码
     * @return
     */
    public List<ExamExamineeEntity> findAllMessage(){
//        List<ExamExamineeEntity> examExaminationEntities=super.findAll();
//        Iterator<ExamExamineeEntity> iterator=examExaminationEntities.iterator();
//        while(iterator.hasNext()){
//           ExamExamineeEntity examExamineeEntity=iterator.next();
//           examExamineeEntity.setPassword("");
//        }
//        return examExaminationEntities;
        List<ExamExamineeEntity> examExaminationEntities=super.findAll();
        return examExaminationEntities;
    }

    /**
     * 获取新的用户ID
     * @return
    */
    public String newUsersId() {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery l = session.createSQLQuery("SELECT MAX(examinee_ID) FROM exam_examinee");
        String id = (String) l.list().get(0);
        Integer idd = Integer.valueOf(id);
        String newid = String.valueOf(idd + 1);
        return newid;
    }

    /**
     * 根据Id删除用户
     * @param examineeId
     * @return
     */
    public boolean deleteById(String examineeId){
        ExamExamineeEntity entity=findById(examineeId);
        System.out.println(entity.getName());
        if(entity==null){
            System.out.println(false);
            return false;
        }
        delete(entity);
        return true;
    }

    /**
     * 更新记录
     * @param examineeId
     * @param name
     * @param phone
     * @param areaId
     * @param sex
     * @return
     */
    public boolean updateById(String examineeId,String name,String phone,String areaId,String sex){
        ExamExamineeEntity entity=findById(examineeId);
        if(entity==null){
            return false;
        }

        entity.setName(name);
        entity.setPhone(phone);
        entity.setAreaId(areaId);
        entity.setSex(sex);
        update(entity);
        return true;
    }
}
