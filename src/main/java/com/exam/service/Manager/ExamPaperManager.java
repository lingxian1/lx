package com.exam.service.Manager;

import com.exam.common.EasyToken.EasyToken;
import com.exam.common.EasyToken.Token;
import com.exam.common.ErrorCode;
import com.exam.common.Response;
import com.exam.common.dao.ExamPaperDao;
import com.exam.common.dao.QuestionDao;
import com.exam.common.entity.ExamExaminationPaperEntity;
import com.exam.common.entity.ExamQuestionEntity;
import com.exam.common.other.ChangeQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by LX on 2017/8/3.
 */
@RestController
@RequestMapping("/examPaperManager")
public class ExamPaperManager {
    @Autowired
    ExamPaperDao examPaperDao;
    @Autowired
    QuestionDao questionDao;
    private Logger logger = LoggerFactory.getLogger(ExamPaperManager.class);

    @GetMapping("/change")
    public Response addExamPaper(@CookieValue(value = "token", defaultValue = "") String token,
                                 @CookieValue(value = "userId", defaultValue = "") String uid,
                                 @RequestParam(defaultValue = "") String examinationId){
        String status=new EasyToken().checkToken(new Token(uid,token));
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
            List<ExamExaminationPaperEntity> entities= examPaperDao.findBy("examinationId",examinationId);
            List<ChangeQuestion> returndata =new ArrayList<>();
            Iterator<ExamExaminationPaperEntity> iterator=entities.iterator();
            while(iterator.hasNext()){
                ExamExaminationPaperEntity entity=iterator.next();
                ExamQuestionEntity entity1=questionDao.findById(entity.getQuestionId());
                if(entity1!=null) {
                    ChangeQuestion changeQuestion = new ChangeQuestion();
                    changeQuestion.setExaminationId(examinationId);
                    changeQuestion.setQuestionId(entity1.getQuestionId());
                    changeQuestion.setQuestionText(entity1.getQuestionText());
                    changeQuestion.setQuestionType(entity1.getQuestionType());
                    changeQuestion.setScore(entity.getScore());

                    returndata.add(changeQuestion);
                }
            }
            return Response.ok(returndata);
        }
    }


    @PostMapping("/add")
    public Response addExamPaper(@CookieValue(value = "token", defaultValue = "") String token,
                                 @CookieValue(value = "userId", defaultValue = "") String uid,
                                 @RequestBody List<ExamExaminationPaperEntity> entities){
        if(entities==null){
            return Response.error(ErrorCode.SYS_NULL_OBJECT);
        }
        String status=new EasyToken().checkToken(new Token(uid,token));
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
            for(ExamExaminationPaperEntity entity:entities){
                examPaperDao.save(entity);
            }
            return Response.ok();
        }
    }

    @PostMapping("/handle")
    public Response changeQuestion(
            @CookieValue(value = "token", defaultValue = "") String token,
            @CookieValue(value = "userId", defaultValue = "") String uid,
            @CookieValue(value = "examinationId2", defaultValue = "") String examinationId2,
            @RequestParam(defaultValue = "") String oper,
            @RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String examinationId,
            @RequestParam(defaultValue = "") String questionId,
            @RequestParam(defaultValue = "") String score){
        logger.info("examinationId2"+examinationId2);
        logger.info("id"+id);
        logger.info("examintionid"+examinationId);
        logger.info("questionId"+questionId);
        logger.info("score"+score);
        String status=new EasyToken().checkToken(new Token(uid,token));
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
            boolean state = false;
            int scoreInt=0;
            if(!"".equals(score)){
                scoreInt=new Integer(score);
            }
            switch (oper) {
                case "add":
                    state = addQuestion(examinationId,questionId,scoreInt);
                    break;
                case "del":
                    state = delQuestion(examinationId2,id);
                    break;
                case "edit":
                    state = editQuestion(examinationId2,questionId,scoreInt);
                    break;
                default:
            }
            if (state) {
                return Response.ok("操作成功");
            } else {
                return Response.error();
            }
        }
    }


    public boolean addQuestion(String examinationId,String questionId,int score){
//        ExamExaminationPaperEntity entity=new ExamExaminationPaperEntity();
//        entity.setExaminationId(examinationId);
//        entity.setQuestionId(questionId);
//        entity.setScore(score);
//        entity.setAccuracy(1.0);
//        examPaperDao.save(entity);
        return true;
    }

    public boolean delQuestion(String examinationId,String questionId){
        Map<String,String> map=new HashMap<>();
        map.put("examinationId",examinationId);
        map.put("questionId",questionId);
        ExamExaminationPaperEntity entity=examPaperDao.findByIds(map);
        if(entity==null){
            return false;
        }
        examPaperDao.delete(entity);
        return true;
    }
    public boolean editQuestion(String examinationId,String questionId,int score){
        Map<String,String> map=new HashMap<>();
        map.put("examinationId",examinationId);
        map.put("questionId",questionId);
        ExamExaminationPaperEntity entity=examPaperDao.findByIds(map);
        if(entity==null){
            return false;
        }
        entity.setScore(score);
        System.out.println(score);
        examPaperDao.update(entity);
        return true;
    }
}
