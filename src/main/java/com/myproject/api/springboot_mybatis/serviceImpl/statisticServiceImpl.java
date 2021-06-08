package com.myproject.api.springboot_mybatis.serviceImpl;

import com.myproject.api.springboot_mybatis.dao.StatisticDao;
import com.myproject.api.springboot_mybatis.entity.Project;
import com.myproject.api.springboot_mybatis.entity.SearchObj;
import com.myproject.api.springboot_mybatis.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class statisticServiceImpl implements StatisticService {

    @Autowired
    StatisticDao statisticDao;

    public List<Project> getProject(SearchObj searchObj){
        return statisticDao.getProject(searchObj);
    }

    public List<Project> getFile(String projectClass, String projectHead){
        return statisticDao.getFile(projectClass, projectHead);
    }

    public List<Project> getContract(String projectClass, String projectHead){
        return statisticDao.getContract(projectClass, projectHead);
    }

    public List<Project> getTender(String projectClass, String projectHead){
        return statisticDao.getTender(projectClass, projectHead);
    }
}
