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
        return "manager/user";
    }
    @RequestMapping("/signin")
    public String signin(){
        return "signin";
    }
    @RequestMapping("/examinfoExaminees")
    public String examinfo(){
        return "exam/examinfo";
    }
    @RequestMapping("/examsExaminees")
    public String exams(){
        return "exam/exams";
    }
    @RequestMapping("/gradeExaminees")
    public String grade(){
        return "exam/grade";
    }
    @RequestMapping("/exampointExaminees")
    public String exampoint(){
        return "exam/exampoint";
    }
    @RequestMapping("/questionmanager")
    public String question(){
        return "manager/questionmanager";
    }
    @RequestMapping("/questionclassinfoManager")
    public String questionclassinfo(){
        return "manager/questionclassinfo";
    }

    @RequestMapping("/manager")
    public String manager(){
        return "manager/manager";
    }
    @RequestMapping("/createexamManager")
    public String createexam(){
        return "manager/createexam";
    }
    @RequestMapping("/addquestionManager")
    public String addquestion(){
        return "manager/addquestion";
    }
    @RequestMapping("/changequestionManager")
    public String changequestion(){
        return "manager/changequestion";
    }
    @RequestMapping("/randomquestionManager")
    public String randomquestion(){
        return "manager/randomquestion";
    }
    @RequestMapping("/grademanager")
    public String grademanager(){
        return "manager/grademanager";
    }
    @RequestMapping("/gradeAreaManager")
    public String gradeArea(){
        return "manager/gradeArea";
    }
    @RequestMapping("/errorQuestionsManager")
    public String errorQuestions(){
        return "manager/errorQuestion";
    }

    @RequestMapping("/error1")
    public String error1(){
        return "error";
    }
}