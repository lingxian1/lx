package com.exam.service.Manager;

import com.exam.common.EasyToken.EasyToken;
import com.exam.common.EasyToken.Token;
import com.exam.common.ErrorCode;
import com.exam.common.Response;
import com.exam.common.dao.AnswerLogDao;
import com.exam.common.dao.ExamPaperDao;
import com.exam.common.entity.ExamAnswerLogEntity;
import com.exam.common.entity.ExamExaminationPaperEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by LX on 2017/8/8.
 * 错题管理，正确率分析
 */
@RestController
@RequestMapping("/errorQuestion")
public class ErrorQuestionController {
    @Autowired
    ExamPaperDao examPaperDao;
    @Autowired
    AnswerLogDao answerLogDao;
    private Logger logger = LoggerFactory.getLogger(ErrorQuestionController.class);

    @GetMapping
    public Response errorQuestion(@CookieValue(value = "token", defaultValue = "") String token,
                                  @CookieValue(value = "userId", defaultValue = "") String uid,
                                  @RequestParam(defaultValue = "") String examinationId){
        String status=new EasyToken().checkToken(new Token(uid,token));
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
            List<ExamExaminationPaperEntity> paperEntities=examPaperDao.findByexam(examinationId);
            if(paperEntities!=null&&paperEntities.size()!=0){
                for(ExamExaminationPaperEntity paper :paperEntities){
                    List<ExamAnswerLogEntity> answerLogEntities=answerLogDao.findByExamination(examinationId,paper.getQuestionId());
                    if(answerLogEntities!=null){
                        int sumcount=answerLogEntities.size();
                        int rightcount=0;
                        double accuracy=2;
                        for(ExamAnswerLogEntity answerLogEntity:answerLogEntities){
                            if(answerLogEntity.getScoreReal()!=0){
                                rightcount++;
                            }
                        }
                        if(sumcount!=0){
                            accuracy=(double) rightcount/sumcount;
                        }
                        examPaperDao.saveAccuracy(examinationId,paper.getQuestionId(),accuracy);
                    }
                }
            }
            return Response.ok(examPaperDao.findBy("examinationId",examinationId,false));
        }
    }
}
