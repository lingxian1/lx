package com.exam.common.dao;

import com.exam.common.entity.ExamExaminationPaperEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LX on 2017/7/24.
 */
@Component
@Repository
public class ExamPaperDao extends AbstractDao<ExamExaminationPaperEntity>{
    public List<ExamExaminationPaperEntity> findByexam(String examinationId){
        return findBy("examinationId",examinationId);
    }
}
