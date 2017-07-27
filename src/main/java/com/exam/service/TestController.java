package com.exam.service;

import com.exam.common.Response;
import com.exam.common.dao.ExamineeDao;
import com.exam.common.dao.SysUserDao;
import com.exam.common.entity.ExamExamineeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by LX on 2017/7/26.
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    ExamineeDao examineeDao;

    @Autowired
    SysUserDao sysUserDao;

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @PostMapping("/handle")
    public Response login(
            @RequestParam(defaultValue = "") String oper,
            @RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String examineeId,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String phone,
            @RequestParam(defaultValue = "") String areaId,
            @RequestParam(defaultValue = "") String sex){
        logger.info("id-"+id);
        logger.info("exid-"+examineeId);
        logger.info(name);
        logger.info(phone);
        logger.info(areaId);
        logger.info(sex);
        logger.info(oper);
        switch(oper){
            case "add":
                addUser(name,phone,areaId,sex);
                break;
            case  "del":
                delUser();
                break;
            case "edit":
                editUser();
                break;
            default:
        }
        return Response.ok();
    }

    private void editUser() {

    }

    private void delUser() {

    }

    private String addUser(String name,String phone,String areaId,String sex) {
        System.out.println("run add user");
        if("".equals(phone)||phone.length()>11||"".equals(name)){
            return "ERROR";
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
        return "TRUE";
    }

    @GetMapping
    public Response getUser(){
        //todo 请求验证
        return Response.ok(examineeDao.findAll());
    }


}
