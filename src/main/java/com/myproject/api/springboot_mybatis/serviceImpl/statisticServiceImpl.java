package com.myproject.api.springboot_mybatis.serviceImpl;

import com.myproject.api.springboot_mybatis.dao.StatisticDao;
import com.myproject.api.springboot_mybatis.entity.Project;
import com.myproject.api.springboot_mybatis.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class statisticServiceImpl implements StatisticService {

    @Autowired
    StatisticDao statisticDao;

    public List<Project> getProject(String projectClass, String projectHead){
        return statisticDao.getProject(projectClass, projectHead);
    }
}
