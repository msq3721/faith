package com.msq.faith.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.msq.faith.entity.HelpProject;
import com.msq.faith.service.IHelpProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    IHelpProjectService helpProjectService;
    //主页
    @RequestMapping("")
    public String index(){
        return "index/index";
    }
    @RequestMapping("/index")
    public String indexindex(){
        return "redirect:/";
    }
    //注销方法
    @RequestMapping("/logout")
    public String layout(HttpServletRequest request){
        HttpSession session = request.getSession();
        request.getSession().removeAttribute("general_user");
        return "redirect:/";
    }
    //注册页面
    @RequestMapping("/reg")
    public String regPage(){
        return "index/reg";
    }
    @RequestMapping("/editor")
    public String editorTest(){
        return "index/editorTest";
    }
    @RequestMapping("/project")
    public String project(){
        return "index/project";
    }
    @RequestMapping("/project/page")
    public String projectPage(Integer id,Model model){
        HelpProject record = helpProjectService.getById(id);
        model.addAttribute("record",record);
        return "index/projectPage";
    }


    @RequestMapping("/layout")
    public String layoutPage(){
        return "index/layout";
    }
    @RequestMapping("/tes")
    public String testtPage(){
        return "index/test";
    }
}
