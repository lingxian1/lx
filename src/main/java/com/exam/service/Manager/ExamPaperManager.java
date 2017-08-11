package com.exam.service.Manager;

import com.exam.common.EasyToken.EasyToken;
import com.exam.common.EasyToken.Token;
import com.exam.common.ErrorCode;
import com.exam.common.Response;
import com.exam.common.dao.ExamPaperDao;
import com.exam.common.dao.ExaminationDao;
import com.exam.common.dao.QuestionDao;
import com.exam.common.entity.ExamExaminationEntity;
import com.exam.common.entity.ExamExaminationPaperEntity;
import com.exam.common.entity.ExamQuestionEntity;
import com.exam.common.other.ChangeQuestion;
import com.exam.common.other.QuestionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by LX on 2017/8/3.
 * 试卷管理
 */
@RestController
@RequestMapping("/examPaperManager")
public class ExamPaperManager {
    @Autowired
    ExamPaperDao examPaperDao;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    ExaminationDao examinationDao;
    private Logger logger = LoggerFactory.getLogger(ExamPaperManager.class);

    /**
     * 获取绑定的试题 试卷管理
     * @param token
     * @param uid
     * @param examinationId
     * @return
     */
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

    /**
     * TODO
     * 发布一场考试 试卷管理
     * @param token
     * @param uid
     * @param examinationId
     * @return
     */
    @PostMapping("/publish")
    public Response publishexam(@CookieValue(value = "token", defaultValue = "") String token,
                                 @CookieValue(value = "userId", defaultValue = "") String uid,
                                @RequestParam(defaultValue = "") String examinationId){
        String status=new EasyToken().checkToken(new Token(uid,token));
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
            ExamExaminationEntity entity1= examinationDao.findById(examinationId);
            if(entity1==null){
                return Response.error(ErrorCode.EXAM_ID_ERROR);
            }else if(entity1.getIsDel().equals("00")){
                return Response.error(ErrorCode.EXAM_PUBLISH_ERROR);
            }else {
                int count = entity1.getQuestionCount();
                int score = entity1.getExaminationScoreAll();
                int realcount = examPaperDao.findByexamCount(examinationId);
                int realscore = examPaperDao.sumScore(examinationId);
                if(count<=realcount&&score<=realscore){
                    examinationDao.publishById(examinationId);
                    return Response.ok("发布成功");
                }else{
                    return Response.error(ErrorCode.EXAM_PUBLISH_SCORE_ERROR);
                }
            }
        }
    }

    /**
     * 根据分类返回该分类下试题数量（题型数量）
     * @param token
     * @param uid
     * @param questionClass
     * @return
     */
    @GetMapping("/findQuestion")
    public Response findQuestion(@CookieValue(value = "token", defaultValue = "") String token,
                                 @CookieValue(value = "userId", defaultValue = "") String uid,
                                 @RequestParam(defaultValue = "") String questionClass){
        String status=new EasyToken().checkToken(new Token(uid,token));
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
//            System.out.println("qs1"+questionClass);
            QuestionType type=types(questionClass);
            if(type==null){
                return Response.error(ErrorCode.QUESTION_TYPE_ERROR);
            }
            System.out.println(type.getQuestionAll()+"  "+type.getQuestionSignal());
            return Response.ok(type);
        }
    }
    /**
     * 设置随机试题
     * @param token
     * @param uid
     * @param examinationId2
     * @return
     */
    @PostMapping("/randomQuestion")
    public Response randomQuestion(@CookieValue(value = "token", defaultValue = "") String token,
                                @CookieValue(value = "userId", defaultValue = "") String uid,
                                @RequestParam(defaultValue = "") String examinationId2){
        String status=new EasyToken().checkToken(new Token(uid,token));
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
          return Response.ok();
        }
    }

    /**
     * 删除所有绑定
     * @param token
     * @param uid
     * @param examinationId2
     * @return
     */
    @GetMapping("/deleteAll")
    public Response deleteAll(@CookieValue(value = "token", defaultValue = "") String token,
                              @CookieValue(value = "userId", defaultValue = "") String uid,
                              @CookieValue(value = "examinationId2", defaultValue = "") String examinationId2){
        String status=new EasyToken().checkToken(new Token(uid,token));
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
            examPaperDao.deleteAllQuestion(examinationId2);
            return Response.ok("操作成功");
        }
    }


    /**
     * 保存绑定的试题 试卷管理
     * @param token
     * @param uid
     * @param entities
     * @return
     */
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

    /**
     * 修改绑定的试题 考试管理
     * @param token
     * @param uid
     * @param examinationId2
     * @param oper
     * @param id
     * @param examinationId
     * @param questionId
     * @param score
     * @return
     */
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

    /**
     * 统计分类试题各题型数量
     * @param questionClass
     * @return
     */
    public QuestionType types(String questionClass){
        int s=0;
        int m=0;
        int j=0;
        System.out.println("questionClass"+questionClass);
        //拦截空值
        if("".equals(questionClass)){
            return null;
        }
        List<ExamQuestionEntity> entities=questionDao.findQuestions(questionClass);
        if(entities==null||entities.size()==0){
            System.out.println("exam question null");
            return null;
        }
        System.out.println("entity size"+entities.size());
        int count=entities.size();
        Iterator<ExamQuestionEntity> iterator=entities.iterator();
        while(iterator.hasNext()){
            ExamQuestionEntity entity=iterator.next();
            String temp=entity.getQuestionType();
            if(temp.equals("signal")){
                s++;
            }else if(temp.equals("multiple")){
                m++;
            }else if(temp.equals("judgement")){
                j++;
            }
        }
        QuestionType questionType=new QuestionType();
        questionType.setQuestionAll(count);
        questionType.setQuestionSignal(s);
        questionType.setQuestionMultiple(m);
        questionType.setQuestionJudgement(j);

        return questionType;
    }
}
