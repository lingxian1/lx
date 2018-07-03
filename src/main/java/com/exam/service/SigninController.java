package com.exam.service;

import com.exam.common.EasyToken.EasyToken;
import com.exam.common.Response;
import com.exam.common.dao.ExamineeDao;
import com.exam.common.entity.ExamExamineeEntity;
import com.exam.common.util.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.exam.common.ErrorCode.*;

/**
 * Created by LX on 2017/7/21.
 * 考生登录
 */
@RestController
@RequestMapping("/signin")
public class SigninController {
    @Autowired
    ExamineeDao examineeDao;

    private Logger logger = LoggerFactory.getLogger(SigninController.class);

    /**
     * 考生登录
     * @param phone
     * @param password
     * @return
     */
    @PostMapping
    public Response login(
            @RequestParam(defaultValue = "") String phone,
            @RequestParam String password){
        logger.info(phone);
        logger.info(password);
        if(phone.length()>15||password.length()>32){
            return Response.error(DATA_ERROR);
        }
        ExamExamineeEntity examExamineeEntity=examineeDao.findByStr("phone",phone);
        if(examExamineeEntity==null){
            return Response.error(USER_EMPTY);
        }else if(examExamineeEntity.getIdentity()!=null && examExamineeEntity.getIdentity().equals("2")){
            return Response.error(USER_DELETE);
        }
        String temp=examExamineeEntity.getSalt()+password;
        if(examExamineeEntity.getPassword().equals(Md5Utils.stringMD5(temp))){
//            examExamineeEntity.setPassword("");//不返回密码
//            logger.info(examExamineeEntity.getExamineeId());
//            return Response.ok(examExamineeEntity);
            return Response.ok(new EasyToken().createToken(examExamineeEntity.getExamineeId(),password,"examinees"));
        }
        else
            return Response.error(PHONE_OR_PASSWORD_ERROR);
    }

}
