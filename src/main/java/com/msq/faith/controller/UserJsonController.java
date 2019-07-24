package com.msq.faith.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.msq.faith.entity.GeneralUser;

import com.msq.faith.entity.HelpProject;
import com.msq.faith.entity.JsonEntity;
import com.msq.faith.service.IGeneralUserService;

import com.msq.faith.service.IHelpProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user/json")
public class UserJsonController {
    @Autowired
    IGeneralUserService generalUserService;
    @Autowired
    IHelpProjectService helpProjectService;


    @RequestMapping(value=  "/test",method = RequestMethod.GET)
    public List<GeneralUser> usertest(){
        return generalUserService.list();
    }

    @RequestMapping(value=  "/pwdval",method = RequestMethod.POST)
    public JsonEntity pwdval (@RequestBody GeneralUser user, HttpServletRequest request){
        System.out.println(user.getMobile());
        System.out.println(user.getPwd());
        GeneralUser record = generalUserService.getOne(
                new LambdaQueryWrapper<GeneralUser>()
                .eq(GeneralUser::getMobile,user.getMobile())
                .eq(GeneralUser::getPwd,user.getPwd())
        );
        if (record == null)
            return new JsonEntity("err","用户与密码不一致");
        else{
            HttpSession session =request.getSession();
            session.setAttribute("general_user",record);
            return new JsonEntity("success","登陆成功");
        }

    }
    @RequestMapping(value = "/reg",method = RequestMethod.POST)
    public JsonEntity reg(@RequestBody GeneralUser user,HttpServletRequest request){
        System.out.println(user.toString());
        GeneralUser record = generalUserService.getOne(
                new LambdaQueryWrapper<GeneralUser>()
                        .eq(GeneralUser::getMobile,user.getMobile())
        );
        if (record != null){
            return new JsonEntity("err","手机号已被注册");
        }
        boolean f = generalUserService.save(user);
        if (f){
             record = generalUserService.getOne(
                    new LambdaQueryWrapper<GeneralUser>()
                            .eq(GeneralUser::getMobile,user.getMobile())
            );
            request.getSession().setAttribute("general_user",record);
            return new JsonEntity("success","注册成功");
        }else {
            return new JsonEntity("err","注册失败");
        }
    }
    @RequestMapping(value = "/revise",method = RequestMethod.POST)
    public JsonEntity revise(@RequestBody GeneralUser user,HttpServletRequest request){
        boolean f = generalUserService.saveOrUpdate(user);
        if (f){
            request.getSession().setAttribute("general_user",user);
            return new JsonEntity("success","修改成功");
        }else {
            return new JsonEntity("err","修改失败");
        }
    }
    @RequestMapping(value = "/project/create",method = RequestMethod.POST)
    public JsonEntity projectCreate(@RequestBody HelpProject helpProject, HttpServletRequest request){
        System.out.println("hello");
        GeneralUser user = (GeneralUser) request.getSession().getAttribute("general_user");
        helpProject.setUserId(user.getId()).setStatus(1);
        boolean f = helpProjectService.saveOrUpdate(helpProject);
        System.out.println(helpProject.toString());
        if (f){
            return new JsonEntity<>("success","创建成功",helpProject.getId());
        }else {
            return new JsonEntity("err","创建失败");
        }

    }
    @RequestMapping(value = "/project/logo/upload")
    public String projectLogoUpload(@RequestParam("file") MultipartFile file,@RequestParam("projectId")Integer id) throws FileNotFoundException {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID()+suffixName;
       // String filePath= ResourceUtils.getURL("classpath:").getPath()+"/static/";
        File path2 = new File(ResourceUtils.getURL("classpath:static").getPath().replace("%20"," ").replace('/', '\\'));
        File upload2 = new File(path2.getAbsolutePath(),"/logo/");
//        String filePath = "D:/";
        File dest = new File(upload2.getAbsolutePath() + "/"+fileName);
        helpProjectService.update(
                new UpdateWrapper<HelpProject>().lambda()
                    .set(HelpProject::getLogo,fileName)
                        .set(HelpProject::getStatus,2)
                        .eq(HelpProject::getId,id));
        System.out.println(dest.getAbsolutePath());
        try {
            file.transferTo(dest);
            return "127.0.0.1:7749/logo/"+ fileName;
        } catch (IOException e) {
        }
        return "OKKKKK";
    }

    @RequestMapping(value = "/project/create/description",method = RequestMethod.POST)
    public JsonEntity createDescription(@RequestBody HelpProject helpProject){
        if (helpProject.getDescription().length() > 1048576){
            return new JsonEntity("err","图片太大");
        }
        if (helpProjectService.update(
                new LambdaUpdateWrapper<HelpProject>()
                .set(HelpProject::getDescription,helpProject.getDescription())
                .set(HelpProject::getStatus,3)
                .eq(HelpProject::getId,helpProject.getId())
        )){
            return new JsonEntity("success","创建成功");
        }
        else {
            return new JsonEntity("err","写入失败");
        }
    }
    @RequestMapping(value = "/project/create/list")
    public JsonEntity projectCreateList(HttpServletRequest request){
        GeneralUser generalUser = (GeneralUser)request.getSession().getAttribute("general_user");
        List<HelpProject> record = helpProjectService.list(
                new LambdaQueryWrapper<HelpProject>()
                        .select(
                                HelpProject::getId,
                                HelpProject::getStatus,
                                HelpProject::getLogo,
                                HelpProject::getBudget,
                                HelpProject::getEndRaiseDate,
                                HelpProject::getName,
                                HelpProject::getFundAmount)
                        .eq(HelpProject::getUserId,generalUser.getId())
        );
        return new JsonEntity<List>("success","读取成功",record);
    }

}
