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
    public Project getOneProject(int project_id) {
        return ProjectDao.getOneProject(project_id);
    }

    @Override
    public List<Project> getAllProject(Project project) {
        return ProjectDao.getAllProject(project);
    }

    @Override
    public List<Project> getCheckProject(Project project) {
        return ProjectDao.getCheckProject(project);
    }

    @Override
    public List<Staff> getname() {
        return ProjectDao.getname();
    }

    @Override
    public List<Project> getGlobalCheckProject(Project project) {
        return ProjectDao.getGlobalCheckProject(project);
    }

    @Override
    public void globalPass(Project project) {
        ProjectDao.globalPass(project);
    }

    @Override
    public void globalRefuse(Project project) {
        ProjectDao.globalRefuse(project);
    }

    @Override
    public List<Project> getArchiveProject() {
        return ProjectDao.getArchiveProject();
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

    @Override
    public List<Project> getAllCheckProject() {
        return ProjectDao.getAllCheckProject();
    }

    @Override
    public List<Project> getAdmin() {
        return ProjectDao.getAdmin();
    }
}
