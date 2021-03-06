package com.exam.service;

import com.exam.common.ErrorCode;
import com.exam.common.Response;
import com.exam.common.dao.*;
import com.exam.common.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.exam.common.ErrorCode.*;

/**
 * Created by LX on 2017/7/24.
 * 试题处理--用户
 */
@RestController
@RequestMapping("/questionExaminees")
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
     *
     * @param examineeId
     * @param examinationId
     * @return
     */
    @GetMapping
    public Response findExamQuestion(
            @CookieValue(value = "userId", defaultValue = "") String examineeId,
            @CookieValue(value = "examinationIdU", defaultValue = "") String examinationId) {
//        logger.info("findExamQ examineeId"+examineeId);
//        logger.info("findExamQ examinationId"+examinationId);
        //检查考试是否完成
        ExamGradeEntity temp = gradeDao.findGrade(examineeId, examinationId);
        if (temp != null && temp.getExaminationState().equals("00")) {
            return Response.error(EXAM_FINISHED);
        }
        List<ExamQuestionEntity> questionEntities = new ArrayList<>();
        Iterator<ExamExaminationPaperEntity> iterator;
        ExamExaminationEntity examinationEntity = examinationDao.findById(examinationId);
        Integer singleCount = examinationEntity.getsingleCount();
        Integer multipleCount = examinationEntity.getMultipleCount();
        Integer judgementCountCount = examinationEntity.getJudgementCount();

        if (singleCount == null && multipleCount == null && judgementCountCount == null) {
            iterator = examPaperDao.findByexam(examinationId).iterator();
        } else {
            iterator = examPaperDao.findByexamRandom(examinationId, singleCount, multipleCount, judgementCountCount).iterator();
        }

        while (iterator.hasNext()) {
            ExamExaminationPaperEntity paper = iterator.next();
            ExamQuestionEntity question = questionDao.findById(paper.getQuestionId());
            question.setQuestionAnswer("");//答案置空
            questionEntities.add(question);
        }
        return Response.ok(questionEntities);
    }


    /**
     * 处理递交的答案
     *
     * @param examAnswerLogEntitys
     * @return
     */
    @PostMapping("/answer")
    @Transactional(rollbackFor = Exception.class)
    public Response getAnswer(@RequestBody List<ExamAnswerLogEntity> examAnswerLogEntitys) {
        if (examAnswerLogEntitys == null) {
            return Response.error();
        }

        if (examAnswerLogEntitys == null || examAnswerLogEntitys.size() == 0) {
            return Response.error(EXAM_ERROR);
        }
        String examineeId = examAnswerLogEntitys.get(0).getExamineeId();
        String examinationId = examAnswerLogEntitys.get(0).getExaminationId();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ExamExaminationEntity entity = examinationDao.findById(examinationId);
        ExamGradeEntity gradeEntity1 = gradeDao.findGrade(examineeId, examinationId);
        if (entity == null || gradeEntity1 == null) {
            return Response.error(EXAM_ID_ERROR);
        }
        //检查考试是否完成
        if (gradeEntity1 != null && gradeEntity1.getExaminationState().equals("00")) {
            return Response.error(EXAM_FINISHED);
        }

        Timestamp endtime = entity.getExaminationEnd();
        long ansTime = entity.getAnswerTime() * 60 * 1000;
        //检测是否超时
        if (endtime == null || new Long(timestamp.getTime()) > new Long(endtime.getTime()) + ansTime + 15 * 60 * 1000) {
            return Response.error(ErrorCode.EXAM_SUBMIT_OUT_OF_DATE);
        }

        Iterator<ExamAnswerLogEntity> iterator = examAnswerLogEntitys.iterator();
        int grade = 0; //统计总分

        //保存每小题分数
        while (iterator.hasNext()) {
            ExamAnswerLogEntity temp = iterator.next();
//                System.out.println("temp:" + temp.getExamineeAnswer());
            if (temp.getExaminationId() != null && temp.getQuestionId() != null) {
                ExamExaminationPaperEntity exam = examPaperDao.findScore(temp.getExaminationId(), temp.getQuestionId());
                if (exam == null) {
                    System.out.println("a exam is null");
                } else {
                    int score = exam.getScore();
                    String answer = questionDao.findQuestion(temp.getQuestionId()).getQuestionAnswer().toUpperCase();
                    int realScore = 0;
                    if (answer.equals(temp.getExamineeAnswer())) {
                        realScore = score;
                    }
                    grade += realScore;
                    temp.setScoreReal(realScore);
                    temp.setSubmitTime(timestamp);
                    answerLogDao.save(temp);
                }
            }
        }
        //设置成绩状态及时间
        ExamGradeEntity gradeEntity = new ExamGradeEntity();
        gradeEntity.setExamineeId(examineeId);
        gradeEntity.setExaminationId(examinationId);
        gradeEntity.setGrade(grade);
        gradeEntity.setExaminationTime(timestamp);
        gradeEntity.setExaminationState("00");
        gradeDao.save(gradeEntity);
        //考试人数+1
        examinationDao.addExamineeCount(examinationId);
        return Response.ok();
    }

}
