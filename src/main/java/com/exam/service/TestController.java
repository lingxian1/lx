package com.exam.service;

import com.exam.common.Response;
import com.exam.common.dao.ExamineeDao;
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
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @PostMapping("/del")
    public Response login(
            @RequestParam(defaultValue = "") String oper,
            @RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String examineeId,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String phone,
            @RequestParam(defaultValue = "") String areaId,
            @RequestParam(defaultValue = "") String sex){
        logger.info("id"+id);
        logger.info("exid"+examineeId);
        logger.info(name);
        logger.info(phone);
        logger.info(areaId);
        logger.info(sex);
        logger.info(oper);
        return Response.ok();
    }

    @GetMapping
    public Response getUser(){
        return Response.ok(examineeDao.findAll());
    }
}
