package com.exam.service.Manager;

import com.exam.common.EasyToken.EasyToken;
import com.exam.common.EasyToken.Token;
import com.exam.common.ErrorCode;
import com.exam.common.Response;
import com.exam.common.dao.ExaminationDao;
import com.exam.common.dao.SysUserDao;
import com.exam.common.entity.SysUserEntity;
import com.exam.common.util.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.exam.common.ErrorCode.PHONE_OR_PASSWORD_ERROR;
import static com.exam.common.ErrorCode.USER_EMPTY;

/**
 * Created by LX on 2017/7/27.
 * 管理员登陆，管理首页信息
 */
@RestController
@RequestMapping("/manager")
public class AdminLoginController {
    @Autowired
    SysUserDao sysUserDao;
    @Autowired
    ExaminationDao examinationDao;
    private Logger logger = LoggerFactory.getLogger(AdminLoginController.class);

    /**
     * 管理登陆 检查
     * @param userId
     * @param userPassword
     * @return
     */
    @PostMapping
    public Response AdminLogin( @RequestParam(defaultValue = "") String userId,
                                @RequestParam String userPassword) {
        logger.info(userId);
        logger.info(userPassword);
        SysUserEntity user=sysUserDao.findByStr("userId",userId);
        if(user==null){
            return Response.error(USER_EMPTY);
        }
        String enpass= Md5Utils.stringMD5(user.getUserPassword()); //数据库获取密码并获取其MD5值

        if(enpass.equals(userPassword)){
            return Response.ok(new EasyToken().createToken(userId,userPassword));
        }
        else{
            return Response.error(PHONE_OR_PASSWORD_ERROR);
        }
    }

    /**
     * 考试信息  manager页面展示
     * @param token
     * @param uid
     * @return
     */
    @GetMapping("/examinfos")
    public Response examinfos(@CookieValue(value = "token", defaultValue = "") String token,
                              @CookieValue(value = "userId", defaultValue = "") String uid){
        String status=new EasyToken().checkToken(new Token(uid,token));
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
            return Response.ok(examinationDao.findExaminfo());
        }
    }
}
