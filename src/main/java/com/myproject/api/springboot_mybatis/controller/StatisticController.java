package com.myproject.api.springboot_mybatis.controller;

import com.myproject.api.springboot_mybatis.entity.Project;
import com.myproject.api.springboot_mybatis.entity.SearchObj;
import com.myproject.api.springboot_mybatis.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@EnableAutoConfiguration
@RestController
public class StatisticController {

    @Autowired
    StatisticService statisticService;


    @PostMapping(value = "/sta/getAll")
    public List<Integer> getInfo(@RequestBody SearchObj searchObj){
        List<Integer> res = new ArrayList<>();
        //初始化每个月数量为0
        for(int i=0;i<12;i++){
            res.add(0);
        }
        //查找出给定范围的日期,例如，2021-04-15 -》 2021-9-16,返回4月到9月之间改项目负责人负责某类工程的数量
        int startYear = Integer.valueOf(searchObj.getBegin().split("-")[0]);
        int startMonth = Integer.valueOf(searchObj.getBegin().split("-")[1]);
//        int endYear = Integer.valueOf(searchObj.getEnd().split("-")[0]);
        int endMonth = Integer.valueOf(searchObj.getEnd().split("-")[1]);
//        System.out.println(11);

        //工程模块
        if(searchObj.getType().equals("project")){
            //先根据工程负责人和工程类型查出数据
            List<Project> list = statisticService.getProject(searchObj.getProjectClass(),searchObj.getProjectHead());
            for(Project project:list){
                int year = Integer.valueOf(project.getProject_starttime().split("-")[0]);
                int month = Integer.valueOf(project.getProject_starttime().split("-")[1])-1;
                if(year==startYear && month>=startMonth && month<=endMonth){
                    res.set(month,res.get(month)+1);
                }
            }
        }
//        //文档模块，file
//        if(searchObj.getType().equals("file")){
//            //先根据工程负责人和工程类型查出数据
//            List<Project> list = statisticService.getFile(searchObj.getProjectClass(),searchObj.getProjectHead());
//            for(Project project:list){
//                int year = Integer.valueOf(project.getProject_starttime().split("-")[0]);
//                int month = Integer.valueOf(project.getProject_starttime().split("-")[1])-1;
//                if(year==startYear && month>=startMonth && month<=endMonth){
//                    res.set(month,res.get(month)+1);
//                }
//            }
//        }
//        ///合同模块，contract
//        if(searchObj.getType().equals("contract")){
//            //先根据工程负责人和工程类型查出数据
//            List<Project> list = statisticService.getContract(searchObj.getProjectClass(),searchObj.getProjectHead());
//            for(Project project:list){
//                int year = Integer.valueOf(project.getProject_starttime().split("-")[0]);
//                int month = Integer.valueOf(project.getProject_starttime().split("-")[1])-1;
//                if(year==startYear && month>=startMonth && month<=endMonth){
//                    res.set(month,res.get(month)+1);
//                }
//            }
//        }
//        ///投标模块，tender
//        if(searchObj.getType().equals("tender")){
//            //先根据工程负责人和工程类型查出数据
//            List<Project> list = statisticService.getTender(searchObj.getProjectClass(),searchObj.getProjectHead());
//            for(Project project:list){
//                int year = Integer.valueOf(project.getProject_starttime().split("-")[0]);
//                int month = Integer.valueOf(project.getProject_starttime().split("-")[1])-1;
//                if(year==startYear && month>=startMonth && month<=endMonth){
//                    res.set(month,res.get(month)+1);
//                }
//            }
//        }
        return res;

    }
}
