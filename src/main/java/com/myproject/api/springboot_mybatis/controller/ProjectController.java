package com.myproject.api.springboot_mybatis.controller;

import com.alibaba.fastjson.JSONObject;
import com.myproject.api.springboot_mybatis.entity.Project;
import com.myproject.api.springboot_mybatis.entity.Staff;
import com.myproject.api.springboot_mybatis.entity.file;
import com.myproject.api.springboot_mybatis.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Resource
    RedisTemplate<String, Staff> redisTemplate;

    @GetMapping(value = "/project/getAllProject")
    public List<Project> getAllProject(HttpServletRequest request){
        String token=request.getHeader("token");
        Staff s1=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s1.getStaff_in_date());
        int staff_id=s1.getStaff_id();
        System.out.println(s1.getStaff_id());

        List<Staff> s=projectService.getname();
        Project pro = new Project();
        pro.setJing_ban_ren(staff_id);
        List<Project> p=projectService.getAllProject(pro);
        return getProjects(s, p);
    }

    private List<Project> getProjects(List<Staff> s, List<Project> p) {
        for(int i=0;i<p.size();i++)
        {

            for(int j=0;j<s.size();j++)
            {
                if(p.get(i).getJing_ban_ren()==s.get(j).getStaff_id())
                {
                    p.get(i).setStaff_namej(s.get(j).staff_name);
                }
                if(p.get(i).getShen_he_ren()==s.get(j).getStaff_id())
                {
                    p.get(i).setStaff_names(s.get(j).staff_name);
                }
            }
        }

        return p;
    }


    @GetMapping(value = "/project/getCheckProject")
    public List<Project> getCheckProject(HttpServletRequest request)
    {
        String token=request.getHeader("token");
        Staff s1=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s1.getStaff_in_date());
        int staff_id=s1.getStaff_id();
        System.out.println(s1.getStaff_id());
        Project project=new Project();
        project.setShen_he_ren(staff_id);
        List<Staff> s=projectService.getname();
        List<Project> p=projectService.getCheckProject(project);
        return getProjects(s, p);

    }


    @RequestMapping(value = "/project/insert")
    public void insert(Project project,HttpServletRequest request)
    {
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s.getStaff_in_date());
        int staff_id=s.getStaff_id();
        System.out.println(s.getStaff_id());
        project.setJing_ban_ren(staff_id);
        projectService.insert(project);
    }

    @RequestMapping(value = "/project/delete",method = RequestMethod.DELETE)
    public void delete(Project project)
    {
        projectService.delete(project);
    }


    @RequestMapping(value = "/project/update")
    public void update(Project project)
    {
        projectService.update(project);
    }


    @RequestMapping(value = "/project/submit")
    public void submit(Project project,HttpServletRequest request)
    {
        projectService.submit(project);
    }


    @RequestMapping(value = "/project/pass")
    public Map<String,Object> pass(Project project,HttpServletRequest request)
    {
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s.getStaff_in_date());
        int staff_id=s.getStaff_id();
        System.out.println(s.getStaff_id());
        Map<String,Object> result=new HashMap<>();
        project.setShen_he_ren(staff_id);
        List<Project> p=projectService.getAllCheckProject();
        for(int i=0;i<p.size();i++){
            if(p.get(i).getProject_id()==project.getProject_id()){
                if(p.get(i).getIf_issued()!='0'){
                    result.put("code",250);
                    result.put("message","已经有人对此进行了审核操作");
                }
                else {
                    if(p.get(i).getShen_he_ren()==0)
                    {
                        projectService.pass(project);
                        result.put("code",100);
                        result.put("message","操作成功");
                    }
                    else if(p.get(i).getShen_he_ren()!=0)
                    {
                        if(p.get(i).getShen_he_ren()==staff_id)
                        {
                            projectService.pass(project);
                            result.put("code",100);
                            result.put("message","操作成功");
                        }
                        else
                        {
                            result.put("code",300);
                            result.put("message","该处已有审核人进行审核");
                        }
                    }
                }
            }
        }

        return result;
    }


    @RequestMapping(value = "/project/refuse")
    public Map<String,Object> refuse(Project project,HttpServletRequest request)
    {
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s.getStaff_in_date());
        int staff_id=s.getStaff_id();
        System.out.println(s.getStaff_id());
        Map<String,Object> result=new HashMap<>();
        project.setShen_he_ren(staff_id);
        List<Project> p=projectService.getAllCheckProject();
        for(int i=0;i<p.size();i++){
            if(p.get(i).getProject_id()==project.getProject_id()){
                if(p.get(i).getIf_issued()!='0'){
                    result.put("code",250);
                    result.put("message","已经有人对此进行了审核操作");
                }
                else {
                    if(p.get(i).getShen_he_ren()==0)
                    {
                        projectService.refuse(project);
                        result.put("code",100);
                        result.put("message","操作成功");
                    }
                    else if(p.get(i).getShen_he_ren()!=0)
                    {
                        if(p.get(i).getShen_he_ren()==staff_id)
                        {
                            projectService.refuse(project);
                            result.put("code",100);
                            result.put("message","操作成功");
                        }
                        else
                        {
                            result.put("code",300);
                            result.put("message","该处已有审核人进行审核");
                        }
                    }
                }
            }
        }
        return result;
    }


    @RequestMapping(value = "/project/if_delete")
    public void if_delete(Project project)
    {
        projectService.if_delete(project);
    }
}