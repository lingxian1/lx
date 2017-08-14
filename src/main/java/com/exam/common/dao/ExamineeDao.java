package com.exam.common.dao;

import com.exam.common.entity.ExamExamineeEntity;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
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
     * 查找所有考生信息
     * @return
     */
    public List<ExamExamineeEntity> findAllMessage(){
        List<ExamExamineeEntity> examExaminationEntities=super.findAll();
        return examExaminationEntities;
    }

    /**
     * 手机号重复检测
     * @param phone
     * @return
     */
    public boolean checkPhone(String phone){
        List<ExamExamineeEntity> examExaminationEntities=findAll();
        Iterator<ExamExamineeEntity> iterator=examExaminationEntities.iterator();
        boolean flag=true;
        while(iterator.hasNext()){
            ExamExamineeEntity entity=iterator.next();
            if(entity.getPhone().equals(phone)){
                flag=false;
            }
        }
        return flag;
    }
    /**
     * 获取新的考生ID
     * @return
    */
    public String newUsersId() {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery l = session.createSQLQuery("SELECT MAX(examinee_ID) FROM exam_examinee");
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
     * 根据Id删除考生
     * @param examineeId
     * @return
     */
    public boolean deleteById(String examineeId){
        ExamExamineeEntity entity=findById(examineeId);
        System.out.println(entity.getName());
        if(entity==null){
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


//    public int newSql(String examintionId){
//        int sum=0;
//        String e="examination_ID";
//        Session session = sessionFactory.getCurrentSession();
//        StringBuilder builder = new StringBuilder();
//        String sql = builder.append("SELECT * FROM exam_examination_paper WHERE ").append(e).append("= ?")
//                .toString();
//
//        SQLQuery l = session.createSQLQuery(sql);
////        l.setString(0,"examination_ID");
//        l.setString(0,examintionId);
//
//            System.out.println(l.list().toString());
//
//        return sum;
//    }

}
