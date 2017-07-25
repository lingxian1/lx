package com.exam.service;

import com.exam.common.Response;
import com.exam.common.dao.ExamPaperDao;
import com.exam.common.dao.GradeDao;
import com.exam.common.dao.QuestionDao;
import com.exam.common.entity.ExamAnswerLogEntity;
import com.exam.common.entity.ExamExaminationPaperEntity;
import com.exam.common.entity.ExamGradeEntity;
import com.exam.common.entity.ExamQuestionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.exam.common.ErrorCode.EXAM_FINISHED;
import static com.exam.common.ErrorCode.USER_ERROR;

/**
 * Created by LX on 2017/7/24.
 */
@RestController
@RequestMapping("/question")
public class ExamController {
    @Autowired
    ExamPaperDao examPaperDao;

    @Autowired
    QuestionDao questionDao;

    @Autowired
    GradeDao gradeDao;

    private Logger logger = LoggerFactory.getLogger(SigninController.class);

    /**
     * 获取某考试试题
     * @param examineeId
     * @param examinationId
     * @return
     */
    @GetMapping
    public Response findExamQuestion(String examineeId,String examinationId) {
        List<ExamQuestionEntity> questionEntities=new ArrayList<>();
        logger.info("findExamQ"+examineeId);
        if ("".equals(examineeId)||examineeId==null) {
            return Response.error(USER_ERROR);
        }
        //检查考试是否完成
        ExamGradeEntity temp=gradeDao.findGrade(examineeId,examinationId);
        if(temp!=null&&temp.getExaminationState().equals("00")){
            return Response.error(EXAM_FINISHED);
        }
        Iterator<ExamExaminationPaperEntity> iterator = examPaperDao.findByexam(examinationId).iterator();
        while (iterator.hasNext()) {
            ExamExaminationPaperEntity paper=iterator.next();
            logger.info(paper.getQuestionId());

            ExamQuestionEntity question=questionDao.findById(paper.getQuestionId());
            question.setQuestionAnswer("");//答案置空
            questionEntities.add(question);
        }
        return Response.ok(questionEntities);
    }

    @PostMapping("/answer")
    public Response getAnswer(@RequestBody  List<ExamAnswerLogEntity> examAnswerLogEntitys){
        System.out.println("it is a "+examAnswerLogEntitys.get(0).getQuestionId());
        return Response.ok();
    }
}
