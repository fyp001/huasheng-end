package com.myproject.api.springboot_mybatis.serviceImpl;

import com.myproject.api.springboot_mybatis.dao.ProjectDao;
import com.myproject.api.springboot_mybatis.entity.Project;
import com.myproject.api.springboot_mybatis.entity.Staff;
import com.myproject.api.springboot_mybatis.service.ProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Resource
    ProjectDao ProjectDao;

    @Override
    public List<Project> getAllProject() {
        return ProjectDao.getAllProject();
    }

    @Override
    public List<Project> getCheckProject() {
        return ProjectDao.getCheckProject();
    }

    @Override
    public List<Staff> getname() {
        return ProjectDao.getname();
    }

    @Override
    public void insert(Project project) {
        ProjectDao.insert(project);
    }

    @Override
    public void delete(Project project) {
        ProjectDao.delete(project);
    }

    @Override
    public void update(Project project) {
        ProjectDao.update(project);
    }

    @Override
    public void submit(Project project) {
        ProjectDao.submit(project);
    }

    @Override
    public void pass(Project project) {
        ProjectDao.pass(project);
    }

    @Override
    public void refuse(Project project) {
        ProjectDao.refuse(project);
    }

    @Override
    public void if_delete(Project project) {
        ProjectDao.if_delete(project);
    }
}
