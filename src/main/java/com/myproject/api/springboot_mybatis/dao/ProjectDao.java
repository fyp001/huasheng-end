package com.myproject.api.springboot_mybatis.dao;

import com.myproject.api.springboot_mybatis.entity.Project;
import com.myproject.api.springboot_mybatis.entity.Staff;


import java.util.List;

public interface ProjectDao {
    Project getOneProject(int project_id);
    List<Project> getAllProject(Project project);
    List<Project> getCheckProject(Project project);
    List<Project> getGlobalCheckProject(Project project);
    List<Project> getAllCheckProject();
    List <Staff>  getname();
    String getNameById(int staff_id);
    List<Project> getAdmin();
    void insert(Project project);
    void delete(Project project);
    void update(Project project);
    void submit(Project project);
    void pass(Project project);
    void refuse(Project project);
    void if_delete(Project project);

    void globalPass(Project project);

    void globalRefuse(Project project);
}
