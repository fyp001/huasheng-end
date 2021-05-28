package com.myproject.api.springboot_mybatis.controller;

import com.myproject.api.springboot_mybatis.entity.Project;
import com.myproject.api.springboot_mybatis.entity.SearchObj;
import com.myproject.api.springboot_mybatis.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@EnableAutoConfiguration
@RestController
public class StatisticController {

    @Autowired
    StatisticService statisticService;


    @PostMapping(value = "/sta/getAll")
    public List<List<Integer>> getInfo(@RequestBody SearchObj searchObj){
        //第一个list下表表示的是年0-2021年，第二个list下标表示的是每年的月1-12月，
        List<List<Integer>> res = new ArrayList<>();


        //查找出给定范围的日期,例如，2021-04-15 -》 2021-9-16,返回4月到9月之间改项目负责人负责某类工程的数量
        int startYear = Integer.valueOf(searchObj.getBegin().split("-")[0]);
        int startMonth = Integer.valueOf(searchObj.getBegin().split("-")[1])-1;
        int endYear = Integer.valueOf(searchObj.getEnd().split("-")[0]);
        int endMonth = Integer.valueOf(searchObj.getEnd().split("-")[1])-1;

        //初始化
        for(int i=0;i<=endYear;i++){
            res.add(new ArrayList<>());
            for(int j=0;j<12;j++){
                res.get(i).add(0);
            }
        }
        System.out.println(startMonth+" "+endMonth);
        //工程模块
        if(searchObj.getType().equals("project")){
            //先根据工程负责人和工程类型查出数据
            List<Project> list = statisticService.getProject(searchObj.getProjectClass(),searchObj.getProjectHead());
            for(Project project:list){
                int year = Integer.valueOf(project.getProject_starttime().split("-")[0]);
                int month = Integer.valueOf(project.getProject_starttime().split("-")[1])-1;
                if(year>startYear && year<endYear){
                    List<Integer> months = res.get(year);
                    months.set(month,months.get(month)+1);
                    res.set(year,months);
                }else if(year==startYear && year==endYear){
                    if(month>=startMonth && month<=endMonth){
                        List<Integer> months = res.get(year);
                        months.set(month,months.get(month)+1);
                        res.set(year,months);
                    }
                }else if(year==startYear){
                    if(month>=startMonth){
                        List<Integer> months = res.get(year);
                        months.set(month,months.get(month)+1);
                        res.set(year,months);
                    }
                }else if(year==endYear){
                    if(month<=endMonth){
                        List<Integer> months = res.get(year);
                        months.set(month,months.get(month)+1);
                        res.set(year,months);
                    }
                }
            }
        }
        for(int i=0;i<=endYear;i++){
            for(int j=0;j<12;j++){
                if(res.get(i).get(j)!=0)
                    System.out.println(i+"-"+(j+1)+":"+res.get(i).get(j));
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
