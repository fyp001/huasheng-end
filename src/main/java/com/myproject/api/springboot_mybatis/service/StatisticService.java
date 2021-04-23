package com.myproject.api.springboot_mybatis.service;

import com.myproject.api.springboot_mybatis.entity.Project;

import java.util.List;

public interface StatisticService{


    List<Project> getProject(String projectClass, String projectHead);
}


