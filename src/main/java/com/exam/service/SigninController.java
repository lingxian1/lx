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

import static com.exam.common.ErrorCode.PHONE_OR_PASSWORD_ERROR;
import static com.exam.common.ErrorCode.USER_EMPTY;

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
        ExamExamineeEntity examExamineeEntity=examineeDao.findByStr("phone",phone);
        if(examExamineeEntity==null){
            return Response.error(USER_EMPTY);
        }
        String enpass= Md5Utils.stringMD5(examExamineeEntity.getPassword()); //数据库获取密码并获取其MD5值
        if(enpass.equals(password)){
//            examExamineeEntity.setPassword("");//不返回密码
//            logger.info(examExamineeEntity.getExamineeId());
//            return Response.ok(examExamineeEntity);
            return Response.ok(new EasyToken().createToken(examExamineeEntity.getExamineeId(),password));
        }
        else
            return Response.error(PHONE_OR_PASSWORD_ERROR);
    }
}
