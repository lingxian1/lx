package com.exam.service.Manager;

import com.exam.common.EasyToken.EasyToken;
import com.exam.common.EasyToken.Token;
import com.exam.common.ErrorCode;
import com.exam.common.Response;
import com.exam.common.dao.ErrorQuestion;
import com.exam.common.dao.ExamPaperDao;
import com.exam.common.dao.ExaminationDao;
import com.exam.common.dao.QuestionDao;
import com.exam.common.entity.ExamExaminationEntity;
import com.exam.common.entity.ExamQuestionEntity;
import com.exam.common.other.AddQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by LX on 2017/7/31.
 * 考试管理
 */
@RestController
@RequestMapping("/examManager")
public class ExamManager {

    @Autowired
    ExaminationDao examinationDao;
    @Autowired
    ExamPaperDao examPaperDao;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    ErrorQuestion errorQuestion;
    private Logger logger = LoggerFactory.getLogger(ExamManager.class);

    @GetMapping
    public Response create(@CookieValue(value = "token", defaultValue = "") String token,
                           @CookieValue(value = "userId", defaultValue = "") String uid){
        //创建考试
        System.out.println(token);
        System.out.println(uid);
        String status=new EasyToken().checkToken(new Token(uid,token));
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
            List<ExamExaminationEntity> entities=examinationDao.findAll();
            Iterator<ExamExaminationEntity> iterator=entities.iterator();
            while (iterator.hasNext()){
                ExamExaminationEntity entity=iterator.next();
                entity.setAnswerTime(entity.getAnswerTime()/60);
                if(entity.getIsDel().equals("00")){
                    entity.setIsDel("是");
                }else{
                    entity.setIsDel("否");
                }
            }
            return Response.ok(entities);
        }
    }

    @GetMapping("/addquestion")
    public  Response addQuestion(@CookieValue(value = "token", defaultValue = "") String token,
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
                int count=entity1.getQuestionCount();
                int score=entity1.getExaminationScoreAll();
                int realcount=examPaperDao.findByexamCount(examinationId);
                int realscore=examPaperDao.sumScore(examinationId);
                AddQuestion addQuestion=new AddQuestion();
                addQuestion.setCount(count);
                addQuestion.setScore(score);
                addQuestion.setRealcount(realcount);
                addQuestion.setRealscore(realscore);
                return Response.ok(addQuestion);
            }
        }
    }

    @GetMapping("choosequestion")
    public Response chooseQuestion(@CookieValue(value = "token", defaultValue = "") String token,
                                   @CookieValue(value = "userId", defaultValue = "") String uid,
                                   @RequestParam(defaultValue = "") String examinationId,
                                   @RequestParam(defaultValue = "") String questionClass,
                                   @RequestParam(defaultValue = "") String days,
                                   @RequestParam(defaultValue = "") String questionType,
                                   @RequestParam(defaultValue = "") String top){
        String status=new EasyToken().checkToken(new Token(uid,token));
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
            List<ExamQuestionEntity> entities=new ArrayList<>();
            int day=new Integer(days);
            if(!"".equals(top)){
                entities=questionDao.findQuestions(questionClass,day,questionType);
                //去重
                Iterator<ExamQuestionEntity> iterator=entities.iterator();
                while(iterator.hasNext()){
                    ExamQuestionEntity entity=iterator.next();
                    Map<String,String> map=new HashMap<>();
                    map.put("examinationId",examinationId);
                    map.put("questionId",entity.getQuestionId());
                    if(!(examPaperDao.findByIds(map)==null)){
                        iterator.remove();
                    }
                }
            }
            //todo TOP10错题
            return Response.ok(entities);
        }
    }
    @PostMapping("/handle")
    public Response examHandle(@CookieValue(value = "token", defaultValue = "") String token,
                               @CookieValue(value = "userId", defaultValue = "") String uid,
                               @RequestParam(defaultValue = "") String oper,
                               @RequestParam(defaultValue = "") String id,
                               @RequestParam(defaultValue = "") String examinationId,
                               @RequestParam(defaultValue = "") String examinationName,
                               @RequestParam(defaultValue = "") String answerTime,
                               @RequestParam(defaultValue = "") String examinationType,
                               @RequestParam(defaultValue = "") String questionCount,
                               @RequestParam(defaultValue = "") String examinationScoreAll,
                               @RequestParam(defaultValue = "") String examinationStart,
                               @RequestParam(defaultValue = "") String examinationEnd,
                               @RequestParam(defaultValue = "") String examinationInfo,
                               @RequestParam(defaultValue = "") String isDel){
        logger.info("id-"+id);
        logger.info("examinationId-"+examinationId);
        logger.info(examinationName);
        logger.info("ANSWERTIME"+answerTime);
        logger.info(examinationType);
        logger.info(questionCount);
        logger.info(examinationScoreAll);
        logger.info(examinationStart+"");
        logger.info(examinationEnd+"");
        logger.info(examinationInfo+"");
        logger.info(oper);
        logger.info("del:"+isDel);
        String status=new EasyToken().checkToken(new Token(uid,token));
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
            boolean state=false;
            switch(oper){
                case "add":
                    state=addExam(examinationName,answerTime,examinationType,
                            questionCount,examinationScoreAll,examinationStart,
                            examinationEnd,examinationInfo,isDel);
                    break;
                case "del":
                    state=delExam(id);
                    break;
                case "edit":
                    state=editExam(examinationId,examinationName,answerTime,examinationType,
                            questionCount,examinationScoreAll,examinationStart,examinationEnd,
                            examinationInfo,isDel);
                    break;
                default:
            }
            if(state){
                return Response.ok("操作成功");
            }else {
                return Response.error();
            }
        }
    }

    private boolean addExam(String examinationName,String answerTime,String examinationType,
                            String questionCount,String examinationScoreAll,String examinationStart,
                            String examinationEnd,String examinationInfo,String isDel){
        ExamExaminationEntity entity=new ExamExaminationEntity();
        entity.setExaminationId(examinationDao.getNewId());
        entity.setExaminationName(examinationName);
        entity.setAnswerTime(new Integer(answerTime)*60);
        entity.setExaminationType(examinationType);
        entity.setQuestionCount(new Integer(questionCount));
        entity.setExaminationScoreAll(new Integer(examinationScoreAll));
        entity.setExaminationStart(Timestamp.valueOf(examinationStart));
        entity.setExaminationEnd(Timestamp.valueOf(examinationEnd));
        entity.setExaminationInfo(examinationInfo);
        entity.setIsDel(isDel);
        entity.setExamineeCount(0);
        examinationDao.save(entity);
        return true;
    }

    private boolean delExam(String id){
        if(!examinationDao.deleteById(id)){
            return false;
        }
        return true;
    }

    private boolean editExam(String examinationId,String examinationName,String answerTime,String examinationType,
                             String questionCount,String examinationScoreAll,String examinationStart,
                             String examinationEnd,String examinationInfo,String isDel){
            ExamExaminationEntity entity=examinationDao.findById(examinationId);
            if(entity==null){
                return false;
            }
            entity.setExaminationName(examinationName);
            entity.setAnswerTime(new Integer(answerTime)*60);
            entity.setExaminationType(examinationType);
            entity.setQuestionCount(new Integer(questionCount));
            entity.setExaminationScoreAll(new Integer(examinationScoreAll));
            entity.setExaminationStart(Timestamp.valueOf(examinationStart));
            entity.setExaminationEnd(Timestamp.valueOf(examinationEnd));
            entity.setExaminationInfo(examinationInfo);
            entity.setIsDel(isDel);
            examinationDao.update(entity);
            return true;
    }
}
