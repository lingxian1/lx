package com.exam.common.dao;

import com.exam.common.entity.ExamGradeEntity;
import com.exam.common.other.GradeArea;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by LX on 2017/7/24.
 * 考试成绩状态管理
 */
@Component
@Repository
public class GradeDao extends AbstractDao<ExamGradeEntity>{
    private Logger logger = LoggerFactory.getLogger(GradeDao.class);

    /**
     * 根据考试号获取所有成绩
     * @param examinationId
     * @return
     */
    public List<ExamGradeEntity> findGradeForExam(String examinationId){
        List<ExamGradeEntity> entities=findBy("examinationId",examinationId,false);
        if (entities==null||entities.size()==0) {
            return new ArrayList<>();
        }
        return entities;
    }

    /**
     * 根据考试号进行区域成绩统计
     * @param examinationId
     * @return
     */
    public List<GradeArea> findGradeForArea(String examinationId){
        Session session = sessionFactory.getCurrentSession();
        //分类统计 去除无效问题
        Query l = session.createSQLQuery(
                "select area_name areaName,examination_ID examinationId,count(*) examineeCount,avg(grade) gradeAvg\n" +
                        "from exam_grade,exam_examinee,exam_area\n" +
                        "where exam_grade.examinee_ID=exam_examinee.examinee_ID and exam_area.area_ID=exam_examinee.area_ID and examination_ID="+"?\n" +
                        "GROUP BY exam_area.area_ID \n" +
                        "ORDER BY gradeAvg DESC").setString(0,examinationId)
                .setResultTransformer(Transformers.aliasToBean(GradeArea.class));
        List<GradeArea> list=l.list();
        if(list==null||list.size()==0||list.get(0)==null){
            return null;
        }
        return list;
    }

    /**
     * 获取个人分数及状态
     * @param examineeId
     * @param examinationId
     * @return
     */
    public ExamGradeEntity findGrade(String examineeId,String examinationId){
        Map<String,String> str =new HashMap<>();
        str.put("examineeId",examineeId);
        str.put("examinationId",examinationId);
        ExamGradeEntity grade=findByIds(str);
        //空值处理
        if(grade==null){
            grade=new ExamGradeEntity();
            grade.setExaminationState("is null");
        }
        return grade;
    }

    /**
     * 获取个人所有考试成绩
     * @param examineeId
     * @return
     */
    public List<ExamGradeEntity> findGrades(String examineeId){
        return findBy("examineeId",examineeId);
    }
}
