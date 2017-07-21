package com.exam.common.dao;

import com.exam.common.entity.ExamExaminationEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

/**
 * Created by LX on 2017/7/21.
 */
@Component
@Repository
public class ExaminationDao extends AbstractDao<ExamExaminationEntity>{
    //找出所有在有效时间的考试
    public List<ExamExaminationEntity> findAll(){
        List<ExamExaminationEntity> exam = super.findAll();
        if (exam.size() == 0) {
            return null;
        }
        Timestamp d = new Timestamp(System.currentTimeMillis());
        Iterator<ExamExaminationEntity> iterator= exam.iterator();
        while (iterator.hasNext()){
            ExamExaminationEntity temp=iterator.next();
            //判断是否在有效时间
            if(d.getTime()<temp.getExaminationStart().getTime()||d.getTime()>temp.getExaminationEnd().getTime()){
                iterator.remove();
            }
        }
        return exam;
    }
}
