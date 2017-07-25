package com.exam.common.dao;

import com.exam.common.entity.ExamAnswerLogEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

/**
 * Created by LX on 2017/7/25.
 */
@Component
@Repository
public class AnswerLogDao extends AbstractDao<ExamAnswerLogEntity> {
    public void saveAll(List<ExamAnswerLogEntity> list){
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        Iterator<ExamAnswerLogEntity> iterator= list.iterator();
        while (iterator.hasNext()){
            ExamAnswerLogEntity temp=iterator.next();
            temp.setSubmitTime(timestamp);
            save(temp);
        }
    }
}
