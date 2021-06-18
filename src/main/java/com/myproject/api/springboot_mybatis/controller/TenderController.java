package com.myproject.api.springboot_mybatis.controller;

import com.myproject.api.springboot_mybatis.entity.Staff;
import com.myproject.api.springboot_mybatis.entity.Tender;
import com.myproject.api.springboot_mybatis.service.TenderService;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class TenderController {
    @Autowired
    TenderService tenderService;

    @Resource
    RedisTemplate<String, Staff> redisTemplate;

    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");


    @CrossOrigin
    @GetMapping(value = "/tender/getAdminTender")
    public List<Tender> getAdminTender(HttpServletRequest request){
        List<Staff> s=tenderService.getname();
        List<Tender> t=tenderService.getAllTender();
        List<Tender> t1=getTenders(s,t);
        for(int i=0;i<t1.size();i++){
            if(t1.get(i).getFile_location()!=null)
            {
                String filename=t1.get(i).getTxt_name();
                String filelocation=t1.get(i).getFile_location();
                String url="http://8.129.86.121:8080/file/download1?fileName="+filename+"&fileLocation="+filelocation;
                t1.get(i).setFile_url(url);
            }
        }
        Collections.reverse(t1);
        return t1;
    }

    @CrossOrigin
    @GetMapping(value = "/tender/getAllTender")
    public List<Tender> getAllTenderByIssue(HttpServletRequest request){
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        int staff_id=s.getStaff_id();
        List<Tender> tenders=tenderService.getAllTender();
        List<Tender> result = new ArrayList<Tender>();
        for(Tender t : tenders){
            if(t.getJing_ban_ren()==staff_id)
            {
                    if(t.getShen_he_ren()!=0){
                        t.setStaff_names(tenderService.GetName(t.getShen_he_ren()));
                    }
                    if(t.getFile_location()!=null)
                    {
                        String filename=t.getTxt_name();
                        String filelocation=t.getFile_location();
                        String url="http://8.129.86.121:8080/tender/download1?fileName="+filename+"&fileLocation="+filelocation;
                        t.setFile_url(url);
                    }
                    result.add(t);
            }
       }
        Collections.reverse(result);
        return result;
    }

    @CrossOrigin
    @GetMapping(value = "/tender/application")
    public List<Tender> getTenderByCurrentRole(@RequestParam(required = false) String current_role){
        List<Tender> tenders=tenderService.getAllTender();
        List<Tender> result = null;
        for(Tender t : tenders){
            if("0".equals(t.getIf_issued())&&current_role.equals(t.getJing_ban_ren())){
                result.add(t);
            }
        }
        return result;
    }

    @RequestMapping(value = "/tender/insert")
    public Map<String,Object> insert(Tender tender, HttpServletRequest request, @RequestParam(value = "files",required = false) MultipartFile[] multipartFiles)
    {
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s.getStaff_id());
        int staff_id=s.getStaff_id();

        Map<String,Object> result=new HashMap<>();
        //在文件操作中，不用/或者\最好，推荐使用File.separator
        String driname = "tenders";
        String rootPath = System.getProperty("user.dir")+ File.separator +driname + File.separator + formatter.format(new Date()) + File.separator;
        //保存文件地址名
        String file_location = null;
        String txt_name = null;
        String newname = null;
        try {
            if (multipartFiles != null&&multipartFiles.length!=0)
            {
                //多文件打包压缩存储
                List<File> files=new ArrayList<>();
                //ZipOutputStream类：完成文件或文件夹的压缩
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
                //以最后一个文件名来命名压缩文件
                try {
                    File zip = new File(rootPath,newname+".zip");
                    file_location = URLEncoder.encode(rootPath, "utf-8");
                    txt_name = URLEncoder.encode(newname+".zip", "utf-8");

                    zip.setWritable(true, false);
                    zip.createNewFile();


                    byte[] buf = new byte[1024];
                    ZipOutputStream out = null;
                    //ZipOutputStream类：完成文件或文件夹的压缩
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
        tender.setFile_location(file_location);
        tender.setTxt_name(txt_name);
        tender.setFile_uploaddate(formatter.format(new Date()));
        tender.setFile_updatedate(formatter.format(new Date()));
        tender.setJing_ban_ren(staff_id);
        tender.setIf_submit('0');
        tender.setIf_issued('0');
        tender.setIf_delete('0');
        tenderService.insert(tender);
        return result;
    }
    @RequestMapping("/tender/delete")
    public Map<String,Object> delete(Tender tender, HttpServletResponse response, HttpServletRequest request)
    {
        Map<String,Object> result=new HashMap<>();
        tenderService.delete(tender);
        if(!tender.getFile_location().equals("null")&&!tender.getTxt_name().equals("null"))
        {

            File file=new File(URLDecoder.decode(tender.getFile_location())+URLDecoder.decode(tender.getTxt_name()));
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


//    @CrossOrigin
//    @RequestMapping(value = "/tender/update")
//    public void update(Tender tender)
//    {
//        tenderService.update(tender);
//    }

    @RequestMapping(value = "/tender/update")
    public Map<String,Object> update(Tender tender,@RequestParam(value = "files",required = false) MultipartFile[] multipartFiles) throws UnsupportedEncodingException {
        String driname ="tenders";
        String rootPath = System.getProperty("user.dir")+ File.separator +driname + File.separator + formatter.format(new Date()) + File.separator;
        //保存文件地址名
        String file_location = null;
        String txt_name = null;
        String newname = null;
        Map<String,Object> result=new HashMap<>();
        if (multipartFiles != null&&multipartFiles.length!=0)
        {
           //多文件打包压缩存储
            List<File> files=new ArrayList<>();
            //ZipOutputStream类：完成文件或文件夹的压缩
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
            //以最后一个文件名来命名压缩文件
            try {
                File zip = new File(rootPath,newname+".zip");
                file_location = URLEncoder.encode(rootPath, "utf-8");
                txt_name = URLEncoder.encode(newname+".zip", "utf-8");

                zip.setWritable(true, false);
                zip.createNewFile();


                byte[] buf = new byte[1024];
                ZipOutputStream out = null;
                //ZipOutputStream类：完成文件或文件夹的压缩
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
            if(!tender.getFile_location().equals("null")&&!tender.getTxt_name().equals("null"))
            {
                //删除旧文件
                //查找出旧文件存放地址
                String filLocation = URLDecoder.decode(tender.getFile_location(),"UTF-8")+ File.separator +
                        URLDecoder.decode(tender.getTxt_name(),"UTF-8");
                System.out.println(filLocation);
                File oldFile = new File(filLocation);
                System.out.println(oldFile.getAbsolutePath());
                oldFile.delete();
                tender.setTxt_name(txt_name);
            }
            else if(tender.getFile_location().equals("null")&&tender.getTxt_name().equals("null")) {
                tender.setTxt_name(txt_name);
                tender.setFile_location(file_location);
            }
        }
        else{
            if(tender.getFile_location().equals("null")&&tender.getTxt_name().equals("null"))
            {
                tender.setFile_location(null);
                tender.setTxt_name(null);
            }
        }
        tender.setFile_updatedate(formatter.format(new Date()));
        tenderService.update(tender);
        return result;
    }


//    @CrossOrigin
//    @RequestMapping(value="/tender/submit")
//    public void submit(Tender tender){
//        tenderService.submitTender(tender);
//    }

    @RequestMapping(value = "/tender/submit")
    public void submit(Tender tender,HttpServletRequest request)
    {
        tenderService.submitTender(tender);
    }



    @RequestMapping(value = "/tender/pass")
    public Map<String,Object> pass(Tender tender,HttpServletRequest request)
    {
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s.getStaff_id());
        int staff_id=s.getStaff_id();
        Map<String,Object> result=new HashMap<>();
        tender.setShen_he_ren(staff_id);
        List<Tender> p=tenderService.getCheckerTender();
        for(int i=0;i<p.size();i++){
            if(p.get(i).getId()==tender.getId()){
                if(p.get(i).getIf_issued()!='0'){
                    result.put("code",250);
                    result.put("message","已经有人对此进行了审核操作");
                }
                else {
                    if(p.get(i).getShen_he_ren()==0)
                    {
                        tenderService.pass(tender);
                        result.put("code",100);
                        result.put("message","操作成功");
                    }
                    else if(p.get(i).getShen_he_ren()!=0)
                    {
                        if(p.get(i).getShen_he_ren()==staff_id)
                        {
                            tenderService.pass(tender);
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



    private List<Tender> getTenders(List<Staff> s, List<Tender> p) {
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


    @GetMapping(value = "/tender/getCheckTender")
    public List<Tender> getCheckTender(HttpServletRequest request)
    {
        String token=request.getHeader("token");
        Staff s1=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s1.getStaff_id());
        int staff_id=s1.getStaff_id();
        Tender tender=new Tender();
        tender.setShen_he_ren(staff_id);
        List<Staff> s=tenderService.getname();
        List<Tender> p=tenderService.getCheckTender(tender);
        List<Tender> p1=getTenders(s,p);
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

    @RequestMapping(value = "/tender/refuse")
    public Map<String,Object> refuse(Tender tender,HttpServletRequest request)
    {
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s.getStaff_id());
        int staff_id=s.getStaff_id();
        Map<String,Object> result=new HashMap<>();
        tender.setShen_he_ren(staff_id);
        List<Tender> p=tenderService.getCheckerTender();
        for(int i=0;i<p.size();i++){
            if(p.get(i).getId()==tender.getId()){
                if(p.get(i).getIf_issued()!='0'){
                    result.put("code",250);
                    result.put("message","已经有人对此进行了审核操作");
                }
                else {
                    if(p.get(i).getShen_he_ren()==0)
                    {
                        tenderService.refuse(tender);
                        result.put("code",100);
                        result.put("message","操作成功");
                    }
                    else if(p.get(i).getShen_he_ren()!=0)
                    {
                        if(p.get(i).getShen_he_ren()==staff_id)
                        {
                            tenderService.refuse(tender);
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


    @RequestMapping(value = "/tender/if_delete")
    public void if_delete(Tender tender)
    {
        tenderService.if_delete(tender);
    }
    /**
     * 项目文件下载
     */
    @RequestMapping("/tender/download1")
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
