package com.myproject.api.springboot_mybatis.controller;

import com.alibaba.fastjson.JSONObject;
import com.myproject.api.springboot_mybatis.entity.Project;
import com.myproject.api.springboot_mybatis.entity.Staff;
import com.myproject.api.springboot_mybatis.entity.file;
import com.myproject.api.springboot_mybatis.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Resource
    RedisTemplate<String, Staff> redisTemplate;

    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping(value = "/project/getAllProject")
    public List<Project> getAllProject(HttpServletRequest request){
        String token=request.getHeader("token");
        Staff s1=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s1.getStaff_id());
        int staff_id=s1.getStaff_id();
        List<Staff> s=projectService.getname();
        Project pro = new Project();
        pro.setJing_ban_ren(staff_id);
        List<Project> p=projectService.getAllProject(pro);
        List<Project> p1=getProjects(s,p);
        for(int i=0;i<p1.size();i++){
            if(p1.get(i).getFile_location()!=null)
            {
//                String filename=URLEncoder.encode(f.get(i).getFile_name(), "utf-8");
//                String filelocation=URLEncoder.encode(f.get(i).getFile_location(), "utf-8");
                String filename=p1.get(i).getTxt_name();
                String filelocation=p1.get(i).getFile_location();
                String url="http://8.129.86.121:8080/file/download1?fileName="+filename+"&fileLocation="+filelocation;
                p1.get(i).setFile_url(url);
            }
        }
        Collections.reverse(p1);
        return p1;
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
        Collections.reverse(p);
        return p;
    }


    @GetMapping(value = "/project/getCheckProject")
    public List<Project> getCheckProject(HttpServletRequest request)
    {
        String token=request.getHeader("token");
        Staff s1=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s1.getStaff_id());
        int staff_id=s1.getStaff_id();
        Project project=new Project();
        project.setShen_he_ren(staff_id);
        List<Staff> s=projectService.getname();
        List<Project> p=projectService.getCheckProject(project);
        List<Project> p1=getProjects(s,p);
        for(int i=0;i<p1.size();i++){
            if(p1.get(i).getFile_location()!=null)
            {
//                String filename=URLEncoder.encode(f.get(i).getFile_name(), "utf-8");
//                String filelocation=URLEncoder.encode(f.get(i).getFile_location(), "utf-8");
                String filename=p1.get(i).getTxt_name();
                String filelocation=p1.get(i).getFile_location();
                String url="http://8.129.86.121:8080/file/download1?fileName="+filename+"&fileLocation="+filelocation;
                p1.get(i).setFile_url(url);
            }
        }
        Collections.reverse(p1);
        return p1;
    }


    @RequestMapping(value = "/project/insert")
    public Map<String,Object> insert(Project project,HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "file",required = false) MultipartFile multipartFiles)
    {
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s.getStaff_id());
        int staff_id=s.getStaff_id();

        Map<String,Object> result=new HashMap<>();
        //在文件操作中，不用/或者\最好，推荐使用File.separator
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        String driname = "projects";
        String rootPath = System.getProperty("user.dir")+ File.separator +driname + File.separator + formatter.format(new Date()) + File.separator;

        try {
            if (multipartFiles != null)
            {
                //String newname=UUID.randomUUID()+"_"+multipartFiles.getOriginalFilename();
                String newname= UUID.randomUUID().toString().replace("-","")+"_"+multipartFiles.getOriginalFilename();
                project.setFile_location(URLEncoder.encode(rootPath, "utf-8"));
                project.setTxt_name(URLEncoder.encode(newname, "utf-8"));
                File fileDir = new File(rootPath);
                if (!fileDir.exists() && !fileDir.isDirectory())
                {
                    fileDir.mkdirs();
                }
                try {
                    //String extension=multipartFiles[i].getOriginalFilename().substring(multipartFiles[i].getOriginalFilename().lastIndexOf("."));
                    multipartFiles.transferTo(new File(fileDir,newname));
                    //String url=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/Springbootfile/"+newname;
                    result.put("status","success");
                    result.put("file_loaction",fileDir);
                    result.put("file_name",newname);
                } catch (IOException e) {
                    result.put("status","fail");
                    result.put("msg",e.getMessage());
                }
            }
            else
            {
                result.put("msg","not file");
            }
        } catch (Exception e) {
            result.put("status","fail");
            result.put("msg",e.getMessage());
        }
        project.setFile_uploaddate(formatter.format(new Date()));
        project.setFile_updatedate(formatter.format(new Date()));
        project.setJing_ban_ren(staff_id);
        projectService.insert(project);
        return result;
    }

    @RequestMapping("/project/delete")
    public Map<String,Object> delete(Project project,HttpServletResponse response,HttpServletRequest request)
    {
        Map<String,Object> result=new HashMap<>();
        projectService.delete(project);
        if(!project.getFile_location().equals("null")&&!project.getTxt_name().equals("null"))
        {

            File file=new File(URLDecoder.decode(project.getFile_location())+URLDecoder.decode(project.getTxt_name()));
            if(!file.exists())
            {
                result.put("status","fail");
                result.put("message","删除文件失败，请检查文件是否存在");
            }
            else
            {
                file.delete();
                result.put("status","success");
                result.put("message","删除文件成功");
            }
        }
        return result;
    }


    @RequestMapping(value = "/project/update")
    public Map<String,Object> update(Project project,@RequestParam(value = "file",required = false) MultipartFile multipartFiles)
    {
        Map<String,Object> result=new HashMap<>();
        if (multipartFiles != null)
        {
            File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
            String desktopPath = desktopDir.getAbsolutePath();
            String driname = "projects";
            String rootPath = System.getProperty("user.dir")+ File.separator +driname + File.separator + formatter.format(new Date()) + File.separator;
            if(!project.getFile_location().equals("null")&&!project.getTxt_name().equals("null"))
            {
                File fileDir = new File(URLDecoder.decode(project.getFile_location()));
                if (!fileDir.exists() && !fileDir.isDirectory())
                {
                    fileDir.mkdirs();
                }
                File file=new File(URLDecoder.decode(project.getFile_location())+URLDecoder.decode(project.getTxt_name()));
                file.delete();
                try {
                    String newname=UUID.randomUUID().toString().replace("-","")+"_"+multipartFiles.getOriginalFilename();
                    multipartFiles.transferTo(new File(fileDir,newname));
                    project.setTxt_name(URLEncoder.encode(newname, "utf-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(project.getFile_location().equals("null")&&project.getTxt_name().equals("null"))
            {
                System.out.println("aaaaa");
                File fileDirnew = new File(rootPath);
                if (!fileDirnew.exists() && !fileDirnew.isDirectory())
                {
                    fileDirnew.mkdirs();
                }
                try {
                    String newname=UUID.randomUUID().toString().replace("-","")+"_"+multipartFiles.getOriginalFilename();
                    multipartFiles.transferTo(new File(fileDirnew,newname));
                    project.setTxt_name(URLEncoder.encode(newname, "utf-8"));
                    project.setFile_location(URLEncoder.encode(rootPath, "utf-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            if(project.getFile_location().equals("null")&&project.getTxt_name().equals("null"))
            {
                project.setFile_location(null);
                project.setTxt_name(null);
            }
        }
        project.setFile_updatedate(formatter.format(new Date()));
        projectService.update(project);
        return result;
    }


    @RequestMapping(value = "/project/submit")
    public void submit(Project project)
    {
        projectService.submit(project);
    }


    @RequestMapping(value = "/project/pass")
    public Map<String,Object> pass(Project project,HttpServletRequest request)
    {
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s.getStaff_id());
        int staff_id=s.getStaff_id();
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
        System.out.println("通过了拦截器到达controller先取值:"+s.getStaff_id());
        int staff_id=s.getStaff_id();
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

    /**
     * 项目文件下载
     */
    @RequestMapping("/project/download1")
    public Map<String,Object> downloadFile(@RequestParam String fileName, @RequestParam String fileLocation, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String,Object> result=new HashMap<>();
//        ServletOutputStream os = null;
//        InputStream is= null;
        File file=new File(URLDecoder.decode(fileLocation)+URLDecoder.decode(fileName));
        if(!file.exists()){
            result.put("status","fail");
            result.put("message","下载文件失败，请检查文件:" + fileName + " 是否存在");
        }
        else
        {
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                result.put("status","success");
                result.put("message","下载成功！");
            } catch (Exception e) {
                result.put("status","fail  ");
                result.put("message","下载出错了！");
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    /**
     * 文件超出大小的异常捕获
     */
    @RestControllerAdvice
    public class MyExceptionHandler {
        /* spring默认上传大小1MB 超出大小捕获异常MaxUploadSizeExceededException */
        @ExceptionHandler(MaxUploadSizeExceededException.class)
        public Map handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
            return Map.of("status", 250, "message", "文件大小超出100MB限制, 请压缩文件! ");
        }
    }
}