package com.exam.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by LX on 2017/7/20.
 */
@Controller
public class HelloController {
    @RequestMapping("/")
    public String hello(){
        return "hello";
    }
    @RequestMapping("/signin")
    public String hello2(){
        return "signin";
    }
    @RequestMapping("/maininfo")
    public String hello3(){
        return "maininfo";
    }
}