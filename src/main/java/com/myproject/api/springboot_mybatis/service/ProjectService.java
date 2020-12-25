package com.myproject.api.springboot_mybatis.service;

import com.myproject.api.springboot_mybatis.entity.Project;
import com.myproject.api.springboot_mybatis.entity.Staff;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProject();
    List<Project> getCheckProject();
    void insert(Project project);
    void delete(Project project);
    void update(Project project);
    void submit(Project project);
    void pass(Project project);
    void refuse(Project project);
    void if_delete(Project project);

    List <Staff>  getname();
}
