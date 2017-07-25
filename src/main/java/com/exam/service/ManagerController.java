package com.exam.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主控制器
 * Created by LX on 2017/7/20.
 */
@Controller
public class ManagerController {
    @RequestMapping("/")
    public String hello(){
        return "hello";
    }
    @RequestMapping("/signin")
    public String hello2(){
        return "/signin";
    }
    @RequestMapping("/exam/examinfo")
    public String hello3(){
        return "/exam/examinfo";
    }
    @RequestMapping("/exam/exams")
    public String hello4(){
        return "/exam/exams";
    }
}