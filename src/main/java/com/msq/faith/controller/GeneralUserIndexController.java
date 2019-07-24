package com.msq.faith.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user/index")
@Controller
public class GeneralUserIndexController {
    //用户主页
    @RequestMapping("")
    public String userIndexPage(){
        System.out.println("用户主页");
        return "index/userIndex";
    }
    @RequestMapping("/test")
    public String userTestPage(){
        System.out.println("用户主页");
        return "index/reg";
    }

}
