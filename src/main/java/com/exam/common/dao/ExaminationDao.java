package com.exam.common.dao;

import com.exam.common.entity.ExamExaminationEntity;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by LX on 2017/7/21.
 * 考试信息管理
 */
@Component
@Repository
public class ExaminationDao extends AbstractDao<ExamExaminationEntity>{

    /**
     * 找出所有在有效时间的考试
     * @return
     */
    public List<ExamExaminationEntity> findUseful(){
        List<ExamExaminationEntity> exam = findAll();
        if (exam.size() == 0) {
            return null;
        }
        Timestamp d = new Timestamp(System.currentTimeMillis());
        Iterator<ExamExaminationEntity> iterator= exam.iterator();
        while (iterator.hasNext()){
            ExamExaminationEntity temp=iterator.next();
            //判断是否在有效时间
            if(d.getTime()<temp.getExaminationStart().getTime()||
                    d.getTime()>temp.getExaminationEnd().getTime()||
                    temp.getIsDel().equals("01")){
                iterator.remove();
            }
        }
        return exam;
    }

    /**
     * 修改考试人数计数器
     * @param examinationId
     */
    public void addExamineeCount(String examinationId){
        ExamExaminationEntity examExaminationEntity=findById(examinationId);
        int count=0;
        if(examExaminationEntity.getExamineeCount()!=null){
            count=examExaminationEntity.getExamineeCount();
        }
        examExaminationEntity.setExamineeCount(count+1);
        update(examExaminationEntity);
    }

    /**
     * 生成新的考试Id
     * @return
     */
    public String getNewId(){
        Session session = sessionFactory.getCurrentSession();
        //Id格式8位日期+两位编号 2017080101
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String prefix = year + (month < 10 ? "0" + month : month) + (day < 10 ? "0" + day : day);

        StringBuilder builder = new StringBuilder();
        String sql = builder.append("SELECT MAX(examination_ID) FROM exam_examination WHERE examination_ID LIKE '")
                .append(prefix)
                .append("%' ")
                .toString();
        SQLQuery l = session.createSQLQuery(sql);
        List list = l.list();
        String id = (String) list.get(0);
        if (id == null || "null".equals(id)) {
            //当天没有，生成新的
            id = prefix + "01";
            System.out.println("id:"+id);
            return id;
        } else {
            long idd = Long.valueOf(id);
            String newid = String.valueOf(idd + 1);
            System.out.println("newid+"+newid);
            return newid;
        }
    }

    /**
     * 根据Id设置考试状态
     * @param examinationId
     * @return
     */
    public boolean deleteById(String examinationId){
        ExamExaminationEntity entity=findById(examinationId);
        if(entity==null){
            return false;
        }
        if(entity.getIsDel().equals("00")){
            return false;
        }
        delete(entity);
        return true;
    }

    /**
     * 试题发布
     * @param examinationId
     * @return
     */
    public boolean publishById(String examinationId){
        ExamExaminationEntity entity=findById(examinationId);
        if(entity==null){
            return false;
        }
        entity.setIsDel("01");
        update(entity);
        return true;
    }

}
