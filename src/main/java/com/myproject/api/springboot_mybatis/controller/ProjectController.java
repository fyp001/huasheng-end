package com.myproject.api.springboot_mybatis.controller;

import com.myproject.api.springboot_mybatis.entity.Project;
import com.myproject.api.springboot_mybatis.entity.Staff;
import com.myproject.api.springboot_mybatis.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;
    static int staff_id=1;
    @CrossOrigin
    @GetMapping(value = "/project/getAllProject")
    public List<Project> getAllProject(){
        List<Staff> s=projectService.getname();
        List<Project> p=projectService.getAllProject();
        return getProjects(s, p);
    }

    private List<Project> getProjects(List<Staff> s, List<Project> p) {
        for(int i=0;i<p.size();i++)
        {

            for(int j=0;j<s.size();j++)
            {
                if(p.get(i).getjing_ban_ren()==s.get(j).getStaff_id())
                {
                    p.get(i).setStaff_namej(s.get(j).staff_name);
                }
                if(p.get(i).getshen_he_ren()==s.get(j).getStaff_id())
                {
                    p.get(i).setStaff_names(s.get(j).staff_name);
                }
            }
        }

        return p;
    }

    @CrossOrigin
    @GetMapping(value = "/project/getCheckProject")
    public List<Project> getCheckProject()
    {

        List<Staff> s=projectService.getname();
        List<Project> p=projectService.getCheckProject();
        return getProjects(s, p);

    }

    @CrossOrigin
    @RequestMapping(value = "/project/insert")
    public void insert(Project project)
    {
        project.setjing_ban_ren(staff_id);
        projectService.insert(project);
    }

    @CrossOrigin
    @RequestMapping(value = "/project/delete",method = RequestMethod.DELETE)
    public void delete(Project project)
    {
        projectService.delete(project);
    }

    @CrossOrigin
    @RequestMapping(value = "/project/update")
    public void update(Project project)
    {
        projectService.update(project);
    }

    @CrossOrigin
    @RequestMapping(value = "/project/submit")
    public void submit(Project project)
    {
        projectService.submit(project);
    }

    @CrossOrigin
    @RequestMapping(value = "/project/pass")
    public void pass(Project project)
    {
        project.setshen_he_ren(staff_id);
        projectService.pass(project);
    }

    @CrossOrigin
    @RequestMapping(value = "/project/refuse")
    public void refuse(Project project)
    {
        project.setshen_he_ren(staff_id);
        projectService.refuse(project);
    }

    @CrossOrigin
    @RequestMapping(value = "/project/if_delete")
    public void if_delete(Project project)
    {
        projectService.if_delete(project);
    }
}