package com.exam.service.Manager;

import com.exam.common.EasyToken.EasyToken;
import com.exam.common.Response;
import com.exam.common.dao.SysUserDao;
import com.exam.common.entity.SysUserEntity;
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
 * Created by LX on 2017/7/27.
 * 管理员登陆
 */
@RestController
@RequestMapping("/manager")
public class AdminLoginController {
    @Autowired
    SysUserDao sysUserDao;

    private Logger logger = LoggerFactory.getLogger(AdminLoginController.class);

    /**
     * 登陆检查
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
}
