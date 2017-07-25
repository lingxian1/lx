package com.exam.service;

import com.exam.common.Response;
import com.exam.common.dao.*;
import com.exam.common.entity.ExamAnswerLogEntity;
import com.exam.common.entity.ExamExaminationPaperEntity;
import com.exam.common.entity.ExamGradeEntity;
import com.exam.common.entity.ExamQuestionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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
    ExaminationDao examinationDao;

    @Autowired
    ExamPaperDao examPaperDao;

    @Autowired
    QuestionDao questionDao;

    @Autowired
    GradeDao gradeDao;

    @Autowired
    AnswerLogDao answerLogDao;

    private Logger logger = LoggerFactory.getLogger(ExamController.class);

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
    @Transactional(rollbackFor = Exception.class)
    public Response getAnswer(@RequestBody  List<ExamAnswerLogEntity> examAnswerLogEntitys){
        Iterator<ExamAnswerLogEntity> iterator= examAnswerLogEntitys.iterator();
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        //TODO 空值判断，错误值判断 验证
        //保存每小题分值
        while (iterator.hasNext()){
            ExamAnswerLogEntity temp=iterator.next();
            int score=examPaperDao.findScore(temp.getExaminationId(),temp.getQuestionId()).getScore();
            String answer=questionDao.findQuestion(temp.getQuestionId()).getQuestionAnswer();
            int realScore=0;
            System.out.println(temp.getQuestionId()+"sorce:"+score+"  answer:"+answer);
            if(answer.equals(temp.getExamineeAnswer())){
                realScore=score;
            }
            temp.setScoreReal(realScore);
            temp.setSubmitTime(timestamp);
            answerLogDao.save(temp);
        }

        String examineeId=examAnswerLogEntitys.get(0).getExamineeId();
        String examinationId=examAnswerLogEntitys.get(0).getExaminationId();
        //保存成绩
//        ExamGradeEntity gradeEntity=new ExamGradeEntity();
//        int grade =answerLogDao.getGrade(examineeId,examinationId);
//        gradeEntity.setExamineeId(examineeId);
//        gradeEntity.setExaminationId(examinationId);
//        gradeEntity.setGrade(grade);
//        gradeEntity.setExaminationState("00");

        //考试人数+1
        examinationDao.addExamineeCount(examinationId);
        return Response.ok();
    }
}
