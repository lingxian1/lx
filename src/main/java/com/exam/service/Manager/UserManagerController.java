package com.exam.service.Manager;

import com.exam.common.EasyToken.EasyToken;
import com.exam.common.EasyToken.Token;
import com.exam.common.ErrorCode;
import com.exam.common.Response;
import com.exam.common.dao.ExamineeDao;
import com.exam.common.dao.SysUserDao;
import com.exam.common.entity.ExamExamineeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 * Created by LX on 2017/7/26.
 * 考生信息管理
 */
@RestController
@RequestMapping("/users")
public class UserManagerController {
    @Autowired
    ExamineeDao examineeDao;

    @Autowired
    SysUserDao sysUserDao;

    private Logger logger = LoggerFactory.getLogger(UserManagerController.class);

    /**
     * 返回所有考生信息 考生信息管理
     * @param userId
     * @param token
     * @return
     */
    @GetMapping
    public Response getUser(@RequestParam(defaultValue = "") String userId,
                            @RequestParam(defaultValue = "")String token){
        logger.info(userId);
        logger.info(token);
        Token token1=new Token(userId,token);
        String status=new EasyToken().checkToken(token1);
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
            List<ExamExamineeEntity> examExaminationEntities=examineeDao.findAllMessage();
            Iterator<ExamExamineeEntity> iterator=examExaminationEntities.iterator();
            while(iterator.hasNext()){
                ExamExamineeEntity examExamineeEntity=iterator.next();
                examExamineeEntity.setPassword("");
            }
            return Response.ok(examExaminationEntities);
        }
    }

    /**
     * 编辑信息 考生信息管理
     * @param token
     * @param uid
     * @param oper
     * @param id
     * @param examineeId
     * @param name
     * @param phone
     * @param areaId
     * @param sex
     * @return
     */
    @PostMapping("/handle")
    public Response login(
            @CookieValue(value = "token", defaultValue = "") String token,
            @CookieValue(value = "userId", defaultValue = "") String uid,
            @RequestParam(defaultValue = "") String oper,
            @RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String examineeId,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String phone,
            @RequestParam(defaultValue = "") String areaId,
            @RequestParam(defaultValue = "") String sex){
        String status=new EasyToken().checkToken(new Token(uid,token));
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
            boolean state = false;
            switch (oper) {
                case "add":
                    state = addUser(name, phone, areaId, sex);
                    break;
                case "del":
                    System.out.println("ss");
                    state = delUser(id);
                    break;
                case "edit":
                    state = editUser(examineeId, name, phone, areaId, sex);
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

    /**
     * 更新用户
     * @param examineeId
     * @param name
     * @param phone
     * @param areaId
     * @param sex
     * @return
     */
    private boolean editUser(String examineeId,String name,String phone,String areaId,String sex) {
        if("".equals(phone)||phone.length()>11||"".equals(name)){
            return false;
        }
        if(!examineeDao.updateById(examineeId,name,phone,areaId,sex)){
            return false;
        }
        return true;
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    private boolean delUser(String id) {
        if(!examineeDao.deleteById(id)){
            return false;
        }
        return true;
    }

    /**
     * 添加用户
     * @param name
     * @param phone
     * @param areaId
     * @param sex
     * @return
     */
    private boolean addUser(String name,String phone,String areaId,String sex) {
        if("".equals(phone)||phone.length()>11||"".equals(name)){
            return false;
        }
        ExamExamineeEntity examinee=new ExamExamineeEntity();
        examinee.setExamineeId(examineeDao.newUsersId());
        examinee.setName(name);
        examinee.setPassword("123456");
        examinee.setPhone(phone);
        examinee.setIdentity("1");
        examinee.setSex(sex);
        examinee.setAreaId(areaId);
        examineeDao.save(examinee);
        return true;
    }
}
