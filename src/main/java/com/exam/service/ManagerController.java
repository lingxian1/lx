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
        return "test";
    }
    @RequestMapping("/signin")
    public String signin(){
        return "/signin";
    }
    @RequestMapping("/exam/examinfo")
    public String examinfo(){
        return "/exam/examinfo";
    }
    @RequestMapping("/exam/exams")
    public String exams(){
        return "/exam/exams";
    }
    @RequestMapping("/exam/grade")
    public String grade(){
        return "/exam/grade";
    }
}