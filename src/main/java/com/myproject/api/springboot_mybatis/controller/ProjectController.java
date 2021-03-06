package com.myproject.api.springboot_mybatis.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.myproject.api.springboot_mybatis.entity.Project;
import com.myproject.api.springboot_mybatis.entity.Staff;
import com.myproject.api.springboot_mybatis.service.ProjectService;
import com.myproject.api.springboot_mybatis.service.StaffService;
import com.myproject.api.springboot_mybatis.utils.exportfujian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import javax.websocket.server.PathParam;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    StaffService staffService;

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
        return p1;
    }
    @GetMapping(value = "/project/getArchiveProject")
    public List<Project>  getArchiveProject(HttpServletRequest request){
        List<Project> p1=projectService.getArchiveProject();
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
        return p1;
    }

    @GetMapping(value = "/project/getAllProject")
    public List<Project> getAllProject(HttpServletRequest request,@PathParam("projectType") String projectType){
        String token=request.getHeader("token");
        Staff s1=redisTemplate.opsForValue().get(token);
        System.out.println("????????????????????????controller?????????:"+s1.getStaff_id());
        int staff_id=s1.getStaff_id();
        List<Staff> s=projectService.getname();
        Project pro = new Project();
        pro.setProject_type(projectType);
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
    public List<Project> getCheckProject(HttpServletRequest request,@PathParam("projectType") String projectType)
    {
        String token=request.getHeader("token");
        Staff s1=redisTemplate.opsForValue().get(token);
        System.out.println(s1.toString());
        System.out.println("????????????????????????controller?????????:"+s1.getStaff_id());
        int staff_id=s1.getStaff_id();
        Project project=new Project();
        project.setProject_type(projectType);
        List<Project> p;
        List<Project> p1;
        if("1".equals(s1.getStaff_permission())){
            project.setShen_he_ren(staff_id);
            List<Staff> s=projectService.getname();
            p=projectService.getCheckProject(project);
            p1=getProjects(s,p);}
        else
            p1=projectService.getGlobalCheckProject(project);
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
        return p1;
    }


    @RequestMapping(value = "/project/insert")
    public Map<String,Object> insert(Project project,HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "files",required = false) MultipartFile[] multipartFiles)
    {
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("????????????????????????controller?????????:"+s.getStaff_id());
        int staff_id=s.getStaff_id();

        Map<String,Object> result=new HashMap<>();
        //???????????????????????????/??????\?????????????????????File.separator
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        String driname = "projects";
        String rootPath = System.getProperty("user.dir")+ File.separator +driname + File.separator + formatter.format(new Date()) + File.separator;


        //?????????????????????
        String file_location = null;
        String txt_name = null;
        String newname = null;


        try {
            if (multipartFiles != null&&multipartFiles.length!=0)
            {
                //???????????????????????????
                List<File> files=new ArrayList<>();
                //ZipOutputStream???????????????????????????????????????
                for(MultipartFile multipartFile :multipartFiles){
                    newname = UUID.randomUUID().toString().replace("-", "") + "_" + multipartFile.getOriginalFilename();
//                    file_location.add(URLEncoder.encode(rootPath, "utf-8"));
//                    txt_name.add(URLEncoder.encode(newname, "utf-8"));
                    File fileDir = new File(rootPath);
                    File file = new File(fileDir, newname);
                    file.setWritable(true, false);
                    if (!fileDir.exists() && !fileDir.isDirectory()) {
                        fileDir.mkdirs();
                    }
                    try {
                        multipartFile.transferTo(file);
                        result.put("status", "success");
                    } catch (IOException e) {
                        result.put("status", "fail");
                        result.put("msg", e.getMessage());
                    }
                    files.add(file);
                }
                //?????????????????????????????????????????????
                try {
                    File zip = new File(rootPath,newname+".zip");
                    file_location = URLEncoder.encode(rootPath, "utf-8");
                    txt_name = URLEncoder.encode(newname+".zip", "utf-8");

                    zip.setWritable(true, false);
                    zip.createNewFile();


                    byte[] buf = new byte[1024];
                    ZipOutputStream out = null;
                    //ZipOutputStream???????????????????????????????????????
                    out = new ZipOutputStream(new FileOutputStream(zip));
                    for (int i = 0; i < files.size(); i++) {
                        FileInputStream in = new FileInputStream(files.get(i));
                        String filePath="";
                        if (filePath == null)
                            filePath = "";
                        else
                            filePath += "/";
                        out.putNextEntry(new ZipEntry(filePath + files.get(i).getName()));
                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                        out.closeEntry();
                        in.close();
                        files.get(i).delete();
                    }
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
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
        result.put("file_loaction", file_location);
        result.put("file_name", newname+".zip");
        project.setFile_location(file_location);
        project.setTxt_name(txt_name);
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
                result.put("message","????????????????????????????????????????????????");
            }
            else
            {
                file.delete();
                result.put("status","success");
                result.put("message","??????????????????");
            }
        }
        return result;
    }


    @RequestMapping(value = "/project/update")
    public Map<String,Object> update(Project project,@RequestParam(value = "files",required = false) MultipartFile[] multipartFiles) throws Exception
    {
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        String driname = "projects";
        String rootPath = System.getProperty("user.dir")+ File.separator +driname + File.separator + formatter.format(new Date()) + File.separator;
        //?????????????????????
        String file_location = null;
        String txt_name = null;
        String newname = null;

        Map<String,Object> result=new HashMap<>();
        if (multipartFiles != null&&multipartFiles.length!=0)
        {
            //???????????????????????????
            List<File> files=new ArrayList<>();
            //ZipOutputStream???????????????????????????????????????
            for(MultipartFile multipartFile :multipartFiles){
                newname = UUID.randomUUID().toString().replace("-", "") + "_" + multipartFile.getOriginalFilename();
                File fileDir = new File(rootPath);
                File file = new File(fileDir, newname);
                file.setWritable(true, false);
                if (!fileDir.exists() && !fileDir.isDirectory()) {
                    fileDir.mkdirs();
                }
                try {
                    multipartFile.transferTo(file);
                    result.put("status", "success");
                } catch (IOException e) {
                    result.put("status", "fail");
                    result.put("msg", e.getMessage());
                }
                files.add(file);
            }
            //?????????????????????????????????????????????
            try {
                File zip = new File(rootPath,newname+".zip");
                file_location = URLEncoder.encode(rootPath, "utf-8");
                txt_name = URLEncoder.encode(newname+".zip", "utf-8");

                zip.setWritable(true, false);
                zip.createNewFile();


                byte[] buf = new byte[1024];
                ZipOutputStream out = null;
                //ZipOutputStream???????????????????????????????????????
                out = new ZipOutputStream(new FileOutputStream(zip));
                for (int i = 0; i < files.size(); i++) {
                    FileInputStream in = new FileInputStream(files.get(i));
                    String filePath="";
                    if (filePath == null)
                        filePath = "";
                    else
                        filePath += "/";
                    out.putNextEntry(new ZipEntry(filePath + files.get(i).getName()));
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    out.closeEntry();
                    in.close();
                    files.get(i).delete();
                }
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(!project.getFile_location().equals("null")&&!project.getTxt_name().equals("null"))
            {
                //???????????????
                //??????????????????????????????
                String filLocation = URLDecoder.decode(project.getFile_location(),"UTF-8")+ File.separator +
                        URLDecoder.decode(project.getTxt_name(),"UTF-8");
                System.out.println(filLocation);
                File oldFile = new File(filLocation);
                System.out.println(oldFile.getAbsolutePath());
                oldFile.delete();
                project.setTxt_name(txt_name);
            }
            else if(project.getFile_location().equals("null")&&project.getTxt_name().equals("null"))
            {
                project.setTxt_name(txt_name);
                project.setFile_location(file_location);
            }
        }
        else{
            if(project.getFile_location().equals("null")&&project.getTxt_name().equals("null"))
            {
                project.setFile_location(null);
                project.setTxt_name(null);
            }
        }
        result.put("file_loaction", file_location);
        result.put("file_name", newname+".zip");
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
        System.out.println("????????????????????????controller?????????:"+s.getStaff_id());
        int staff_id=s.getStaff_id();
        Map<String,Object> result=new HashMap<>();
        project.setShen_he_ren(staff_id);
        List<Project> p=projectService.getAllCheckProject();
        for(int i=0;i<p.size();i++){
            if(p.get(i).getProject_id()==project.getProject_id()){
                if(p.get(i).getIf_issued()!='0'){
                    result.put("code",250);
                    result.put("message","???????????????????????????????????????");
                }
                else {
                    if(p.get(i).getShen_he_ren()==0)
                    {
                        projectService.pass(project);
                        result.put("code",100);
                        result.put("message","????????????");
                    }
                    else if(p.get(i).getShen_he_ren()!=0)
                    {
                        if(p.get(i).getShen_he_ren()==staff_id)
                        {
                            projectService.pass(project);
                            result.put("code",100);
                            result.put("message","????????????");
                        }
                        else
                        {
                            result.put("code",300);
                            result.put("message","?????????????????????????????????");
                        }
                    }
                }
            }
        }

        return result;
    }
    @RequestMapping(value = "/project/globalpass")
    public void globalpass(Project project,HttpServletRequest request)
    {
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("????????????????????????controller?????????:"+s.getStaff_id());
        projectService.globalPass(project);
    }
    @RequestMapping(value = "/project/globalrefuse")
    public void globalrefuse(Project project,HttpServletRequest request)
    {
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("????????????????????????controller?????????:"+s.getStaff_id());
        projectService.globalRefuse(project);
    }
    @RequestMapping(value = "/project/refuse")
    public Map<String,Object> refuse(Project project,HttpServletRequest request)
    {
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("????????????????????????controller?????????:"+s.getStaff_id());
        int staff_id=s.getStaff_id();
        Map<String,Object> result=new HashMap<>();
        project.setShen_he_ren(staff_id);
        List<Project> p=projectService.getAllCheckProject();
        for(int i=0;i<p.size();i++){
            if(p.get(i).getProject_id()==project.getProject_id()){
                if(p.get(i).getIf_issued()!='0'){
                    result.put("code",250);
                    result.put("message","???????????????????????????????????????");
                }
                else {
                    if(p.get(i).getShen_he_ren()==0)
                    {
                        projectService.refuse(project);
                        result.put("code",100);
                        result.put("message","????????????");
                    }
                    else if(p.get(i).getShen_he_ren()!=0)
                    {
                        if(p.get(i).getShen_he_ren()==staff_id)
                        {
                            projectService.refuse(project);
                            result.put("code",100);
                            result.put("message","????????????");
                        }
                        else
                        {
                            result.put("code",300);
                            result.put("message","?????????????????????????????????");
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
     * ??????????????????
     */
    @RequestMapping("/project/download1")
    public Map<String,Object> downloadFile(@RequestParam String fileName, @RequestParam String fileLocation, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String,Object> result=new HashMap<>();
//        ServletOutputStream os = null;
//        InputStream is= null;
        File file=new File(URLDecoder.decode(fileLocation)+URLDecoder.decode(fileName));
        if(!file.exists()){
            result.put("status","fail");
            result.put("message","????????????????????????????????????:" + fileName + " ????????????");
        }
        else
        {
            // ??????????????????
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // ?????????????????????????????????
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            // ??????????????????
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
                result.put("message","???????????????");
            } catch (Exception e) {
                result.put("status","fail  ");
                result.put("message","??????????????????");
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
    //http://localhost:8080/project/export/289
    public Map<String,Object> exportFile(@PathVariable("projectId") int project_id,HttpServletResponse response) throws IOException, DocumentException {
        Project oneProject = projectService.getOneProject(project_id);
        Map<String,Object> result=new HashMap<>();
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com=fsv.getHomeDirectory();

        String fileName = oneProject.getTxt_name();
        String fileLocation = oneProject.getFile_location();
        File file=new File(URLDecoder.decode(fileLocation)+URLDecoder.decode(fileName));
        if(!file.exists()){
            System.out.println("?????????????????????????????????");
            result.put("status","fail");
            result.put("message","????????????????????????????????????:" + fileName + " ????????????");
            return result;
        }
        // ??????????????????
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // ?????????????????????????????????
//        response.setHeader("Access-Control-Expose-Headers","Authorization");
        String projectName=URLEncoder.encode(oneProject.getProject_name()+".pdf", "UTF-8");
        projectName=projectName.replaceAll("//+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename=" + projectName);
        System.out.println(oneProject.getProject_name());
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());





        //-------------------------



        //----------------------




        Document document = new Document();
        //?????????????????????
        PdfWriter writer = PdfWriter.getInstance(document, outputStream );

//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(com.getPath() +"\\????????????"+ UUID.randomUUID().toString() + ".pdf"));
        //????????????
        document.open();

        //????????????,??????????????????????????????
        BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        Font bf = new Font(bfChinese);
        bf.setSize(10f);
        //????????????
        document.add(new Paragraph(oneProject.getProject_name(),bf));


        //4?????????.
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100); // ??????100%??????
        table.setSpacingBefore(10f); // ?????????
        table.setSpacingAfter(10f); // ?????????


        List<PdfPRow> listRow = table.getRows();
        //????????????
        float[] columnWidths = {2f, 2f, 2f, 2f};
        table.setWidths(columnWidths);

        //???1
        PdfPCell cells1[] = new PdfPCell[4];
        PdfPRow row1 = new PdfPRow(cells1);


        //?????????
        cells1[0] = new PdfPCell(new Paragraph("????????????",bf));//???????????????
        //cells1[0].setBorderColor(BaseColor.BLUE);//????????????
        //cells1[0].setPaddingLeft(20);//?????????20
        cells1[0].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells1[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????

        cells1[1] = new PdfPCell(new Paragraph(oneProject.getProject_name(),bf));
        cells1[2] = new PdfPCell(new Paragraph("??????????????????",bf));
        cells1[2].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells1[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????
        cells1[3] = new PdfPCell(new Paragraph(oneProject.getProject_starttime(),bf));

        //???2
        PdfPCell cells2[] = new PdfPCell[4];
        PdfPRow row2 = new PdfPRow(cells2);
        //?????????
        cells2[0] = new PdfPCell(new Paragraph("?????????",bf));//???????????????
        //cells1[0].setBorderColor(BaseColor.BLUE);//????????????
        //cells2[0].setPaddingLeft(20);//?????????20
        cells2[0].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells2[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????

        cells2[1] = new PdfPCell(new Paragraph(staffService.staffInfoById(oneProject.getJing_ban_ren()).getStaff_name(),bf));
        cells2[2] = new PdfPCell(new Paragraph("?????????",bf));
        cells2[2].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells2[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????
        cells2[3] = new PdfPCell(new Paragraph(staffService.staffInfoById(oneProject.getShen_he_ren()).getStaff_name(),bf));

        //???3
        PdfPCell cells3[] = new PdfPCell[4];
        PdfPRow row3 = new PdfPRow(cells3);

        //?????????
        cells3[0] = new PdfPCell(new Paragraph("??????",bf));//???????????????
        //cells1[0].setBorderColor(BaseColor.BLUE);//????????????
        //cells3[0].setPaddingLeft(20);//?????????20
        cells3[0].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells3[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????
        cells3[0].setFixedHeight(36f);
        cells3[0].setRowspan(2);
        cells3[1] = new PdfPCell(new Paragraph("??????",bf));
        cells3[1].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells3[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????
        cells3[1].setFixedHeight(18f);
        cells3[2] = new PdfPCell(new Paragraph(oneProject.getProject_type(),bf));
        cells3[2].setFixedHeight(18f);
        cells3[2].setColspan(2);

        PdfPCell cellsd[] = new PdfPCell[4];
        PdfPRow rowsd = new PdfPRow(cellsd);
        cellsd[1] = new PdfPCell(new Paragraph("??????",bf));
        cellsd[1].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cellsd[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????
        cellsd[1].setFixedHeight(18f);
        cellsd[2] = new PdfPCell(new Paragraph(oneProject.getProject_class(),bf));
        cellsd[2].setFixedHeight(18f);
        cellsd[2].setColspan(2);

//???4
        PdfPCell cells4[] = new PdfPCell[4];
        PdfPRow row4 = new PdfPRow(cells4);
        //?????????
        cells4[0] = new PdfPCell(new Paragraph("????????????",bf));//???????????????
        //cells1[0].setBorderColor(BaseColor.BLUE);//????????????
        //cells4[0].setPaddingLeft(20);//?????????20
        cells4[0].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells4[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????

        cells4[1] = new PdfPCell(new Paragraph(oneProject.getTxt_name(),bf));
        cells4[1].setColspan(3);


//???4
        PdfPCell cells5[] = new PdfPCell[4];
        PdfPRow row5 = new PdfPRow(cells5);



        String fujian = exportfujian.readZip(URLDecoder.decode(fileLocation)+URLDecoder.decode(fileName));
        //System.out.println(fujian);
        cells5[0] = new PdfPCell(new Paragraph(fujian,bf));//???????????????
        cells5[0].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells5[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????
        cells5[0].setColspan(4);
        cells5[0].setMinimumHeight(500f);





        PdfPCell cells6[] = new PdfPCell[4];
        PdfPRow row6 = new PdfPRow(cells6);
        //?????????
        cells6[0] = new PdfPCell(new Paragraph("????????????",bf));//???????????????
        //cells1[0].setBorderColor(BaseColor.BLUE);//????????????
        //cells2[0].setPaddingLeft(20);//?????????20
        cells6[0].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells6[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????

        cells6[1] = new PdfPCell(new Paragraph(oneProject.getProject_client(),bf));
        cells6[2] = new PdfPCell(new Paragraph("???????????????",bf));
        cells6[2].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells6[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????
        cells6[3] = new PdfPCell(new Paragraph(oneProject.getProject_reportnumber(),bf));


        PdfPCell cells7[] = new PdfPCell[4];
        PdfPRow row7 = new PdfPRow(cells7);
        //?????????
        cells7[0] = new PdfPCell(new Paragraph("???????????????",bf));//???????????????
        //cells1[0].setBorderColor(BaseColor.BLUE);//????????????
        //cells2[0].setPaddingLeft(20);//?????????20
        cells7[0].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells7[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????

        cells7[1] = new PdfPCell(new Paragraph(oneProject.getProject_qualitycontroler(),bf));
        cells7[2] = new PdfPCell(new Paragraph("???????????????",bf));
        cells7[2].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells7[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????
        cells7[3] = new PdfPCell(new Paragraph(oneProject.getProject_head(),bf));


        PdfPCell cells8[] = new PdfPCell[4];
        PdfPRow row8 = new PdfPRow(cells8);
        //?????????
        cells8[0] = new PdfPCell(new Paragraph("????????????",bf));//???????????????
        //cells1[0].setBorderColor(BaseColor.BLUE);//????????????
        //cells2[0].setPaddingLeft(20);//?????????20
        cells8[0].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells8[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????

        cells8[1] = new PdfPCell(new Paragraph(oneProject.getProject_members(),bf));
        cells8[1].setColspan(3);


        PdfPCell cells9[] = new PdfPCell[4];
        PdfPRow row9 = new PdfPRow(cells9);
        //?????????
        cells9[0] = new PdfPCell(new Paragraph("????????????",bf));//???????????????
        //cells1[0].setBorderColor(BaseColor.BLUE);//????????????
        //cells2[0].setPaddingLeft(20);//?????????20
        cells9[0].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells9[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????

        cells9[1] = new PdfPCell(new Paragraph(oneProject.getProject_starttime(),bf));
        cells9[2] = new PdfPCell(new Paragraph("????????????",bf));
        cells9[2].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells9[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????
        cells9[3] = new PdfPCell(new Paragraph(oneProject.getProject_endtime(),bf));


        PdfPCell cells10[] = new PdfPCell[4];
        PdfPRow row10 = new PdfPRow(cells10);
        //?????????
        cells10[0] = new PdfPCell(new Paragraph("??????????????????",bf));//???????????????
        //cells1[0].setBorderColor(BaseColor.BLUE);//????????????
        //cells2[0].setPaddingLeft(20);//?????????20
        cells10[0].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells10[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????

        cells10[1] = new PdfPCell(new Paragraph(oneProject.getProject_comment(),bf));
        cells10[2] = new PdfPCell(new Paragraph("????????????",bf));
        cells10[2].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells10[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????
        cells10[3] = new PdfPCell(new Paragraph(String.valueOf(oneProject.getProject_assets()),bf));


        PdfPCell cells11[] = new PdfPCell[4];
        PdfPRow row11 = new PdfPRow(cells11);
        //?????????
        cells11[0] = new PdfPCell(new Paragraph("???????????????",bf));//???????????????
        //cells1[0].setBorderColor(BaseColor.BLUE);//????????????
        //cells2[0].setPaddingLeft(20);//?????????20
        cells11[0].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells11[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????

        cells11[1] = new PdfPCell(new Paragraph(oneProject.getProject_partner(),bf));
        cells11[2] = new PdfPCell(new Paragraph("????????????",bf));
        cells11[2].setHorizontalAlignment(Element.ALIGN_CENTER);//????????????
        cells11[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//????????????
        cells11[3] = new PdfPCell(new Paragraph(oneProject.getProject_construction(),bf));


        //???????????????????????????
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
        //???????????????????????????
        document.add(table);




        //????????????
        document.close();
        //???????????????
        writer.close();

        outputStream.flush();
        outputStream.close();
        result.put("status","success");
        result.put("message","???????????????");
        return result;

    }


    /**
     * ?????????????????????????????????
     */
    @RestControllerAdvice
    public class MyExceptionHandler {
        /* spring??????????????????1MB ????????????????????????MaxUploadSizeExceededException */
        @ExceptionHandler(MaxUploadSizeExceededException.class)
        public Map handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
            return Map.of("status", 250, "message", "??????????????????100MB??????, ???????????????! ");
        }
    }
}