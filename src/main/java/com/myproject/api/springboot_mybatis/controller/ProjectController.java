package com.myproject.api.springboot_mybatis.controller;

import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
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
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Resource
    RedisTemplate<String, Staff> redisTemplate;

    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping(value = "/project/getAdmin")
    public List<Project> getAdmin(HttpServletRequest request){
        List<Project> p=projectService.getAdmin();
        List<Staff> s=projectService.getname();
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




    @GetMapping(value = "/project/export/{projectId}")
    //http://localhost:8080/project/export/387
    public void exportFile(@PathVariable("projectId") int project_id,HttpServletResponse response) throws IOException, DocumentException {
        Project oneProject = projectService.getOneProject(project_id);

        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com=fsv.getHomeDirectory();
        // 配置文件下载
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
//        response.setHeader("Access-Control-Expose-Headers","Authorization");
        String projectName=URLEncoder.encode(oneProject.getProject_name()+".pdf", "UTF-8");
        projectName=projectName.replaceAll("//+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename=" + projectName);
        System.out.println(oneProject.getProject_name());
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        Document document = new Document();
        //建立一个书写器
        PdfWriter writer = PdfWriter.getInstance(document, outputStream );

//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(com.getPath() +"\\审计报告"+ UUID.randomUUID().toString() + ".pdf"));
        //打开文件
        document.open();

        //中文字体,解决中文不能显示问题
        BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        Font bf = new Font(bfChinese);
        bf.setSize(10f);
        //添加内容
        document.add(new Paragraph("审计报告",bf));


        //4列的表.
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100); // 宽度100%填充
        table.setSpacingBefore(10f); // 前间距
        table.setSpacingAfter(10f); // 后间距


        List<PdfPRow> listRow = table.getRows();
        //设置列宽
        float[] columnWidths = {2f, 2f, 2f, 2f};
        table.setWidths(columnWidths);

        //行1
        PdfPCell cells1[] = new PdfPCell[4];
        PdfPRow row1 = new PdfPRow(cells1);


        //单元格
        cells1[0] = new PdfPCell(new Paragraph("项目名称",bf));//单元格内容
        //cells1[0].setBorderColor(BaseColor.BLUE);//边框验证
        //cells1[0].setPaddingLeft(20);//左填充20
        cells1[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells1[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中

        cells1[1] = new PdfPCell(new Paragraph(oneProject.getProject_name(),bf));
        cells1[2] = new PdfPCell(new Paragraph("项目开始时间",bf));
        cells1[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells1[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cells1[3] = new PdfPCell(new Paragraph(oneProject.getProject_starttime(),bf));

        //行2
        PdfPCell cells2[] = new PdfPCell[4];
        PdfPRow row2 = new PdfPRow(cells2);
        //单元格
        cells2[0] = new PdfPCell(new Paragraph("经办人",bf));//单元格内容
        //cells1[0].setBorderColor(BaseColor.BLUE);//边框验证
        //cells2[0].setPaddingLeft(20);//左填充20
        cells2[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells2[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中

        cells2[1] = new PdfPCell(new Paragraph(String.valueOf(oneProject.getJing_ban_ren()),bf));
        cells2[2] = new PdfPCell(new Paragraph("审核人",bf));
        cells2[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells2[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cells2[3] = new PdfPCell(new Paragraph(String.valueOf(oneProject.getShen_he_ren()),bf));

        //行3
        PdfPCell cells3[] = new PdfPCell[4];
        PdfPRow row3 = new PdfPRow(cells3);

        //单元格
        cells3[0] = new PdfPCell(new Paragraph("类型",bf));//单元格内容
        //cells1[0].setBorderColor(BaseColor.BLUE);//边框验证
        //cells3[0].setPaddingLeft(20);//左填充20
        cells3[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells3[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cells3[0].setFixedHeight(36f);
        cells3[0].setRowspan(2);
        cells3[1] = new PdfPCell(new Paragraph("大类",bf));
        cells3[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells3[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cells3[1].setFixedHeight(18f);
        cells3[2] = new PdfPCell(new Paragraph(oneProject.getProject_type(),bf));
        cells3[2].setFixedHeight(18f);
        cells3[2].setColspan(2);

        PdfPCell cellsd[] = new PdfPCell[4];
        PdfPRow rowsd = new PdfPRow(cellsd);
        cellsd[1] = new PdfPCell(new Paragraph("类别",bf));
        cellsd[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cellsd[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cellsd[1].setFixedHeight(18f);
        cellsd[2] = new PdfPCell(new Paragraph(oneProject.getProject_class(),bf));
        cellsd[2].setFixedHeight(18f);
        cellsd[2].setColspan(2);

//行4
        PdfPCell cells4[] = new PdfPCell[4];
        PdfPRow row4 = new PdfPRow(cells4);
        //单元格
        cells4[0] = new PdfPCell(new Paragraph("附件名称",bf));//单元格内容
        //cells1[0].setBorderColor(BaseColor.BLUE);//边框验证
        //cells4[0].setPaddingLeft(20);//左填充20
        cells4[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells4[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中

        cells4[1] = new PdfPCell(new Paragraph(oneProject.getTxt_name(),bf));
        cells4[1].setColspan(3);


//行4
        PdfPCell cells5[] = new PdfPCell[4];
        PdfPRow row5 = new PdfPRow(cells5);
        //单元格
        cells5[0] = new PdfPCell(new Paragraph("附件内容",bf));//单元格内容
        //cells1[0].setBorderColor(BaseColor.BLUE);//边框验证
        //cells3[0].setPaddingLeft(20);//左填充20
        cells5[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells5[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cells5[1] = new PdfPCell(new Paragraph("xx",bf));//单元格内容
        cells5[1].setColspan(3);
        cells5[1].setFixedHeight(100f);





        PdfPCell cells6[] = new PdfPCell[4];
        PdfPRow row6 = new PdfPRow(cells6);
        //单元格
        cells6[0] = new PdfPCell(new Paragraph("客户名称",bf));//单元格内容
        //cells1[0].setBorderColor(BaseColor.BLUE);//边框验证
        //cells2[0].setPaddingLeft(20);//左填充20
        cells6[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells6[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中

        cells6[1] = new PdfPCell(new Paragraph(oneProject.getProject_client(),bf));
        cells6[2] = new PdfPCell(new Paragraph("审计报告号",bf));
        cells6[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells6[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cells6[3] = new PdfPCell(new Paragraph(oneProject.getProject_reportnumber(),bf));


        PdfPCell cells7[] = new PdfPCell[4];
        PdfPRow row7 = new PdfPRow(cells7);
        //单元格
        cells7[0] = new PdfPCell(new Paragraph("质控负责人",bf));//单元格内容
        //cells1[0].setBorderColor(BaseColor.BLUE);//边框验证
        //cells2[0].setPaddingLeft(20);//左填充20
        cells7[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells7[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中

        cells7[1] = new PdfPCell(new Paragraph(oneProject.getProject_qualitycontroler(),bf));
        cells7[2] = new PdfPCell(new Paragraph("项目负责人",bf));
        cells7[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells7[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cells7[3] = new PdfPCell(new Paragraph(oneProject.getProject_head(),bf));


        PdfPCell cells8[] = new PdfPCell[4];
        PdfPRow row8 = new PdfPRow(cells8);
        //单元格
        cells8[0] = new PdfPCell(new Paragraph("项目组员",bf));//单元格内容
        //cells1[0].setBorderColor(BaseColor.BLUE);//边框验证
        //cells2[0].setPaddingLeft(20);//左填充20
        cells8[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells8[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中

        cells8[1] = new PdfPCell(new Paragraph(oneProject.getProject_members(),bf));
        cells8[1].setColspan(3);


        PdfPCell cells9[] = new PdfPCell[4];
        PdfPRow row9 = new PdfPRow(cells9);
        //单元格
        cells9[0] = new PdfPCell(new Paragraph("开始时间",bf));//单元格内容
        //cells1[0].setBorderColor(BaseColor.BLUE);//边框验证
        //cells2[0].setPaddingLeft(20);//左填充20
        cells9[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells9[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中

        cells9[1] = new PdfPCell(new Paragraph(oneProject.getProject_starttime(),bf));
        cells9[2] = new PdfPCell(new Paragraph("终止时间",bf));
        cells9[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells9[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cells9[3] = new PdfPCell(new Paragraph(oneProject.getProject_endtime(),bf));


        PdfPCell cells10[] = new PdfPCell[4];
        PdfPRow row10 = new PdfPRow(cells10);
        //单元格
        cells10[0] = new PdfPCell(new Paragraph("报告意见类型",bf));//单元格内容
        //cells1[0].setBorderColor(BaseColor.BLUE);//边框验证
        //cells2[0].setPaddingLeft(20);//左填充20
        cells10[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells10[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中

        cells10[1] = new PdfPCell(new Paragraph(oneProject.getProject_comment(),bf));
        cells10[2] = new PdfPCell(new Paragraph("资产金额",bf));
        cells10[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells10[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cells10[3] = new PdfPCell(new Paragraph(String.valueOf(oneProject.getProject_assets()),bf));


        PdfPCell cells11[] = new PdfPCell[4];
        PdfPRow row11 = new PdfPRow(cells11);
        //单元格
        cells11[0] = new PdfPCell(new Paragraph("项目合伙人",bf));//单元格内容
        //cells1[0].setBorderColor(BaseColor.BLUE);//边框验证
        //cells2[0].setPaddingLeft(20);//左填充20
        cells11[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells11[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中

        cells11[1] = new PdfPCell(new Paragraph(oneProject.getProject_partner(),bf));
        cells11[2] = new PdfPCell(new Paragraph("施工单位",bf));
        cells11[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells11[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cells11[3] = new PdfPCell(new Paragraph(oneProject.getProject_construction(),bf));


        //把第一行添加到集合
        listRow.add(row1);
        listRow.add(row2);
        listRow.add(row3);
        listRow.add(rowsd);
        listRow.add(row6);
        listRow.add(row7);
        listRow.add(row8);
        listRow.add(row9);
        listRow.add(row10);
        listRow.add(row11);


        listRow.add(row4);
        listRow.add(row5);
        //把表格添加到文件中
        document.add(table);



        //关闭文档
        document.close();
        //关闭书写器
        writer.close();

        outputStream.flush();
        outputStream.close();
//        Field[] fields = oneProject.getClass().getDeclaredFields();
//        for(Field field : fields){
//            String name = field.getName();
//
//        }

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