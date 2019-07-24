package com.msq.faith.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.msq.faith.entity.AdminUser;
import com.msq.faith.entity.HelpProject;
import com.msq.faith.service.IAdminUserService;
import com.msq.faith.service.IHelpProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    IAdminUserService adminUserService;
    @Autowired
    IHelpProjectService helpProjectService;
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("admin");
        return "redirect:../";
    }
    @RequestMapping("/login")
    public String Index(){
        return "admin/login";
    }
    @RequestMapping("/login/login")
    public String loginlogin(@ModelAttribute(value = "user")AdminUser user, Model model, HttpServletRequest request){
       System.out.println("登录");
       System.out.println(user.toString());
        AdminUser u = adminUserService.getOne(
                new LambdaQueryWrapper<AdminUser>()
                .eq(AdminUser::getWorkNumber,user.getWorkNumber())
                .eq(AdminUser::getPwd,user.getPwd())
        );
        if (u == null){
            model.addAttribute("error","请检用户名或者密码");
            return"admin/login";
        }
        else {
            HttpSession session = request.getSession();
            session.setAttribute("admin",u);
            return "redirect:/admin/index";
        }

    }

    @RequestMapping("/index")
    public String index(Model model){
        return "admin/index";
    }
    @RequestMapping("/project")
    public String projectList(Model model) {
        List<HelpProject> record = helpProjectService.list();
        model.addAttribute("record",record);
        return "admin/project";
    }
    @RequestMapping("/project/edit")
    public String projectHandle(Integer id,Model model) {
        HelpProject record = helpProjectService.getById(id);
        model.addAttribute("record",record);
        return "admin/projectDetail";
    }
    @RequestMapping("/project/edit/handle")
    public String projectEditHandle(HelpProject helpProject) {
        helpProjectService.updateById(helpProject);
        return "redirect:/admin/project";
    }
}
