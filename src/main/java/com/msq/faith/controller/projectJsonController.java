package com.msq.faith.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.msq.faith.entity.HelpProject;
import com.msq.faith.entity.JsonEntity;
import com.msq.faith.entity.Topic;
import com.msq.faith.service.IHelpProjectService;
import com.msq.faith.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.jar.JarEntry;

@RestController
@RequestMapping("/project/json")
public class projectJsonController {
    @Autowired
    IHelpProjectService helpProjectService;
    @Autowired
    ITopicService topicService;
    @RequestMapping("/project")
    public JsonEntity project(){
        List<HelpProject> record= helpProjectService.list(
                new LambdaQueryWrapper<HelpProject>()
                        .select(
                                HelpProject::getId,
                                HelpProject::getStatus,
                                HelpProject::getLogo,
                                HelpProject::getBudget,
                                HelpProject::getEndRaiseDate,
                                HelpProject::getName,
                                HelpProject::getFundAmount)
                .eq(HelpProject::getStatus,10)
        );
        return new JsonEntity<List>("success"," ",record);
    }
    @PutMapping("/topic")
    public JsonEntity topic(@RequestBody Topic topic){
        System.out.println("topic put ok");
        if (topicService.save(topic)){
            return new JsonEntity("success"," ");
        } else{
            return new JsonEntity("err","网络出现了问题");
        }
    }

}
