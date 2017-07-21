package com.exam.service;

import com.exam.common.Response;
import com.exam.common.dao.ExamineeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by LX on 2017/7/20.
 */
@RestController
@RequestMapping("/aaa")
public class Hello2Controller {
    @Autowired
    ExamineeDao examineeDao;

    private Logger logger = LoggerFactory.getLogger(Hello2Controller.class);

    @GetMapping(value = "/{uid}")
    @ResponseBody
    public Response login(){
        return Response.ok(examineeDao.findBy("phone","123456"));
    }

    @PostMapping
    public Response login2(
            @RequestParam(defaultValue = "") String phone,
            @RequestParam String password){
        logger.info(password);
        return Response.ok(examineeDao.findBy("phone",phone));
    }

}
