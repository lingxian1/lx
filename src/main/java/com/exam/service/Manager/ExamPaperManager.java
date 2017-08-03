package com.exam.service.Manager;

import com.exam.common.EasyToken.EasyToken;
import com.exam.common.EasyToken.Token;
import com.exam.common.ErrorCode;
import com.exam.common.Response;
import com.exam.common.dao.ExamPaperDao;
import com.exam.common.entity.ExamExaminationPaperEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by LX on 2017/8/3.
 */
@RestController
@RequestMapping("/examPaperManager")
public class ExamPaperManager {
    @Autowired
    ExamPaperDao examPaperDao;
    private Logger logger = LoggerFactory.getLogger(ExamPaperManager.class);

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
}
