package com.exam.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by LX on 2017/7/20.
 * 主控制器,URL
 */
@Controller
public class ManagerController {
    @RequestMapping("/")
    public String hello(){
        return "AdminLogin";
    }
    @RequestMapping("/usermanager")
    public String UserManager(){
        return "/manager/user";
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
    @RequestMapping("/questionmanager")
    public String question(){
        return "/manager/questionmanager";
    }
    @RequestMapping("/manager")
    public String manager(){
        return "/manager/manager";
    }
    @RequestMapping("/createexam")
    public String createexam(){
        return "/manager/createexam";
    }
    @RequestMapping("/addquestion")
    public String addquestion(){
        return "/manager/addquestion";
    }
}