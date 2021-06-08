package com.myproject.api.springboot_mybatis.dao;

import com.myproject.api.springboot_mybatis.entity.Project;
import com.myproject.api.springboot_mybatis.entity.SearchObj;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Repository
@Mapper
public interface StatisticDao {



    List<Project> getProject(SearchObj searchObj);

    List<Project> getFile(String projectClass, String projectHead);

    List<Project> getContract(String projectClass, String projectHead);

    List<Project> getTender(String projectClass, String projectHead);
}
