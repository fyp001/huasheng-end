package com.myproject.api.springboot_mybatis.dao;

import com.myproject.api.springboot_mybatis.entity.Project;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Repository
@Mapper
public interface StatisticDao {



    List<Project> getProject(String projectClass, String projectHead);
}
