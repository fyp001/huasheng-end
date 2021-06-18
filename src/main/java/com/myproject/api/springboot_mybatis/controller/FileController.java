package com.myproject.api.springboot_mybatis.controller;

import com.myproject.api.springboot_mybatis.entity.MyFile;
import com.myproject.api.springboot_mybatis.entity.Staff;
import com.myproject.api.springboot_mybatis.service.FileService;
import com.myproject.api.springboot_mybatis.service.StaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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

@EnableAutoConfiguration
@RestController
public class FileController {
    @Autowired
    FileService fileservice;
    StaffService staffservice;
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter1= new SimpleDateFormat("yyyy-MM-dd'-'HH-mm-ss");

    @Resource
    RedisTemplate<String, Staff> redisTemplate;

    private static java.util.logging.Logger log = java.util.logging.Logger.getLogger(FileController.class.getName());
    @GetMapping(value = "/file/getAdminFile")
    public List<MyFile> getAdminFile(HttpServletRequest request){
        List<MyFile> f=fileservice.GetAllFile();
        for(int i=0;i<f.size();i++){
            if(f.get(i).getShen_he_ren()!=0){
                f.get(i).setChecker(fileservice.GetName(f.get(i).getShen_he_ren()));
            }
            if(f.get(i).getJing_ban_ren()!=0){
                f.get(i).setOperatorname(fileservice.GetName(f.get(i).getJing_ban_ren()));
            }
            if(f.get(i).getFile_location()!=null)
            {
                String filename=f.get(i).getTxt_name();
                String filelocation=f.get(i).getFile_location();
                String url="http://8.129.86.121:8080/file/download1?fileName="+filename+"&fileLocation="+filelocation;
                f.get(i).setFile_url(url);
            }
        }
        Collections.reverse(f);
        return f;
    }

    @GetMapping(value = "/file/getAdminCon")
    public List<MyFile> getAdminCon(HttpServletRequest request){
        List<MyFile> f=fileservice.GetAllCon();
        for(int i=0;i<f.size();i++){
            if(f.get(i).getShen_he_ren()!=0){
                f.get(i).setChecker(fileservice.GetName(f.get(i).getShen_he_ren()));
            }
            if(f.get(i).getJing_ban_ren()!=0){
                f.get(i).setOperatorname(fileservice.GetName(f.get(i).getJing_ban_ren()));
            }
            if(f.get(i).getFile_location()!=null)
            {
                String filename=f.get(i).getTxt_name();
                String filelocation=f.get(i).getFile_location();
                String url="http://8.129.86.121:8080/file/download1?fileName="+filename+"&fileLocation="+filelocation;
                f.get(i).setFile_url(url);
            }
        }
        Collections.reverse(f);
        return f;
    }

    /**
     * 经办人文档显示
     * 需要获取token验证身份
     * 然后筛选出该身份对应的文档
     */
    @RequestMapping(value = "/file/getOperator")
    List<MyFile> getOperator(HttpServletRequest request){
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s.staff_name);
        int staff_id=s.getStaff_id();
        MyFile file1 = new MyFile();
        file1.setJing_ban_ren(staff_id);
        List<MyFile> f=fileservice.GetOperator(file1);
        System.out.println(f.size());
        for(int i=0;i<f.size();i++){
            if(f.get(i).getShen_he_ren()!=0){
                f.get(i).setChecker(fileservice.GetName(f.get(i).getShen_he_ren()));
            }
            if(f.get(i).getFile_location()!=null)
            {
                String filename=f.get(i).getTxt_name();
                String filelocation=f.get(i).getFile_location();
                String url="http://8.129.86.121:8080/file/download1?fileName="+filename+"&fileLocation="+filelocation;
                f.get(i).setFile_url(url);
            }
        }
        Collections.reverse(f);
        return f;
    }

    /**
     * 审核人文档显示
     * 需要获取token验证身份信息
     * 在这里需要返回审核人的名字与经办人的名字
     */
    @RequestMapping(value = "/file/getChecker")
    List<MyFile> getChecker(HttpServletRequest request){

        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s.getStaff_id());
        int staff_id=s.getStaff_id();
        MyFile file2 = new MyFile();
        file2.setShen_he_ren(staff_id);
        List<MyFile> f=fileservice.GetChecker(file2);
        for(int i=0;i<f.size();i++){
            if(f.get(i).getJing_ban_ren()!=0){
                f.get(i).setOperatorname(fileservice.GetName(f.get(i).getJing_ban_ren()));
            }
            if(f.get(i).getShen_he_ren()!=0){
                f.get(i).setChecker(fileservice.GetName(f.get(i).getShen_he_ren()));
            }
            if(f.get(i).getFile_location()!=null)
            {
                String filename=f.get(i).getTxt_name();
                String filelocation=f.get(i).getFile_location();
                String url="http://8.129.86.121:8080/file/download1?fileName="+filename+"&fileLocation="+filelocation;
                f.get(i).setFile_url(url);
            }
        }
        Collections.reverse(f);
        return f;
    }

    /**
     * 经办人提交文档到审核人审核
     */
    @CrossOrigin
    @RequestMapping(value = "/file/submitfile")
    void submitfile(MyFile f){
        fileservice.submitfile(f);
    }

    /**
     * 审核人审核通过
     * 需要获取token验证身份，因为审核时需要对应到是哪个用户进行的操作
     * 审核之前还需判断该文档/合同是否已经有人进行了审核操作
     */
    @CrossOrigin
    @RequestMapping(value = "/file/checkpass")
    Map<String,Object> checkpass(MyFile f,HttpServletRequest request){
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s.getStaff_id());
        int staff_id=s.getStaff_id();
        Map<String,Object> result=new HashMap<>();
        f.setShen_he_ren(staff_id);
        List<MyFile> l=fileservice.getAllCheckerFile();
        for(int i=0;i<l.size();i++){
            if(l.get(i).getFile_id()==f.getFile_id()){
                if(l.get(i).getIf_issued()!='0'){
                    result.put("code",250);
                    result.put("message","已经有人对此进行了审核操作");
                }
                else {
                    if(l.get(i).getShen_he_ren()==0)
                    {
                        fileservice.checkpass(f);
                        result.put("code",100);
                        result.put("message","操作成功");
                    }
                    else if(l.get(i).getShen_he_ren()!=0)
                    {
                        if(l.get(i).getShen_he_ren()==staff_id)
                        {
                            fileservice.checkpass(f);
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

    /**
     * 审核人审核退回
     * 需要获取token验证身份，因为审核时需要对应到是哪个用户进行的操作
     * 审核之前还需判断该文档/合同是否已经有人进行了审核操作
     */
    @RequestMapping(value = "/file/checknotpass")
    Map<String,Object> checknotpass(MyFile f,HttpServletRequest request){
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s.getStaff_id());
        int staff_id=s.getStaff_id();
        Map<String,Object> result=new HashMap<>();
        f.setShen_he_ren(staff_id);
        List<MyFile> l=fileservice.getAllCheckerFile();
        for(int i=0;i<l.size();i++){
            if(l.get(i).getFile_id()==f.getFile_id()){
                if(l.get(i).getIf_issued()!='0'){
                    result.put("code",250);
                    result.put("message","已经有人对此进行了审核操作");
                }
                else {
                    if(l.get(i).getShen_he_ren()==0)
                    {
                        fileservice.checknotpass(f);
                        result.put("code",100);
                        result.put("message","操作成功");
                    }
                    else if(l.get(i).getShen_he_ren()!=0)
                    {
                        if(l.get(i).getShen_he_ren()==staff_id)
                        {
                            fileservice.checknotpass(f);
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

    /**
     * 审核人删除已审核显示(暂时无用)
     */
    @RequestMapping(value = "/file/checkdelete")
    void checkdelete(MyFile f){
        fileservice.checkdelete(f);
    }

    /**
     * 根据id查询(暂时无用)
     */
    @RequestMapping(value = "/file/QueryFile")
    public List<MyFile> QueryFile(MyFile f) //得到传来的file_type参数 json参数要加@RequestBody使其自动转对象
    {
        return fileservice.QueryFile(f);
    }

    /**
     * 获取所有文档列表(暂时无用)
     */
    @RequestMapping(value = "/file/GetAllFile")
    public List<MyFile> GetAllFile() throws UnsupportedEncodingException {
        List<MyFile> f=fileservice.GetAllFile();
        for(int i=0;i<f.size();i++) {
            if(f.get(i).getFile_location()!=null)
            {
//                String filename=URLEncoder.encode(f.get(i).getFile_name(), "utf-8");
//                String filelocation=URLEncoder.encode(f.get(i).getFile_location(), "utf-8");
                String filename=f.get(i).getTxt_name();
                String filelocation=f.get(i).getFile_location();
                String url="http://8.129.86.121:8080/file/download1?fileName="+filename+"&fileLocation="+filelocation;
                f.get(i).setFile_url(url);
            }
        }
        return f;
    }

    /**
     * 经办人获取所有合同列表
     * 需要获取token验证用户身份
     * 因为经办人只能获取自己对应的合同
     * 所以需要根据用户进行筛选
     */
    @RequestMapping(value = "/file/GetAllContract")
    public List<MyFile> GetAllContract(HttpServletRequest request) throws UnsupportedEncodingException {
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s.getStaff_id());
        int staff_id=s.getStaff_id();
        MyFile file1 = new MyFile();
        file1.setJing_ban_ren(staff_id);
        List<MyFile> f=fileservice.GetAllContract(file1);
        for(int i=0;i<f.size();i++){
            if(f.get(i).getShen_he_ren()!=0){
                f.get(i).setChecker(fileservice.GetName(f.get(i).getShen_he_ren()));
            }
            if(f.get(i).getFile_location()!=null)
            {
                String filename=f.get(i).getTxt_name();
                String filelocation=f.get(i).getFile_location();
                String url="http://8.129.86.121:8080/file/download1?fileName="+filename+"&fileLocation="+filelocation;
                f.get(i).setFile_url(url);
            }
        }
        Collections.reverse(f);
        return f;
    }

    /**
     * 审核人获取所有合同列表
     * 需要获取token验证身份信息
     */
    @RequestMapping(value = "/file/GetAllContractChecker")
    public List<MyFile> GetAllContractChecker(HttpServletRequest request) throws UnsupportedEncodingException {
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        System.out.println("通过了拦截器到达controller先取值:"+s.getStaff_id());
        int staff_id=s.getStaff_id();
        MyFile file2 = new MyFile();
        file2.setShen_he_ren(staff_id);
        List<MyFile> f=fileservice.GetAllContractChecker(file2);
        for(int i=0;i<f.size();i++){
            if(f.get(i).getJing_ban_ren()!=0){
                f.get(i).setOperatorname(fileservice.GetName(f.get(i).getJing_ban_ren()));
            }
            if(f.get(i).getShen_he_ren()!=0){
                f.get(i).setChecker(fileservice.GetName(f.get(i).getShen_he_ren()));
            }
            if(f.get(i).getFile_location()!=null)
            {
                String filename=f.get(i).getTxt_name();
                String filelocation=f.get(i).getFile_location();
                String url="http://8.129.86.121:8080/file/download1?fileName="+filename+"&fileLocation="+filelocation;
                f.get(i).setFile_url(url);
            }
        }
        Collections.reverse(f);
        return f;
    }

    /**
     * 文档插入字段(暂时无用)
     */
    @RequestMapping(value = "/file/insert")
    public void insert(MyFile f)
    {
        f.setFile_uploaddate(formatter.format(new Date()));
        f.setFile_updatedate(formatter.format(new Date()));
        fileservice.insert(f);
    }

    /**
     * 文档删除(暂时无用)
     */
    @RequestMapping(value = "/file/delete")
    public void delete(MyFile f)
    {
        fileservice.delete(f);
    }

    /**
     * 获取文档文件位置(暂时无用)
     */
    @RequestMapping(value = "/file/download")
    List<MyFile> download(MyFile f){
        return fileservice.download(f);
    }

    /**
     * 无用
     */
    @RequestMapping(value = "/file/search")
    List<MyFile> SearchFile(MyFile f){
        return fileservice.SearchFile(f);
    }

    /**
     * 更新文档
     * 可以编辑已有文档的字段，以及上传文件，若该文档原来已有文件，则编辑时上传会替换原文件；
     * 若原来没有文件则上传一个文件保存文件地址
     */
    @RequestMapping(value = "/file/update")
    public Map<String,Object> update(@RequestParam(value = "files",required = false) MultipartFile[] multipartFiles,MyFile f,HttpServletResponse response,HttpServletRequest request) throws UnsupportedEncodingException {
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        String driname = "files";
        String rootPath = System.getProperty("user.dir")+ File.separator +driname + File.separator + formatter.format(new Date()) + File.separator;
        //保存文件地址名
        String file_location = null;
        String txt_name = null;
        String newname = null;
        Map<String,Object> result=new HashMap<>();

        if (multipartFiles != null&&multipartFiles.length!=0)
        {
//            File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
//            String desktopPath = desktopDir.getAbsolutePath();
//            String driname = "files";
//            String rootPath = System.getProperty("user.dir")+ File.separator +driname + File.separator + formatter.format(new Date()) + File.separator;
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
            if(!f.getFile_location().equals("null")&&!f.getTxt_name().equals("null"))
            {
                //删除旧文件
                //查找出旧文件存放地址
                String filLocation = URLDecoder.decode(f.getFile_location(),"UTF-8")+ File.separator +
                        URLDecoder.decode(f.getTxt_name(),"UTF-8");
                System.out.println(filLocation);
                File oldFile = new File(filLocation);
                System.out.println(oldFile.getAbsolutePath());
                oldFile.delete();
                f.setTxt_name(txt_name);
            }
            else if(f.getFile_location().equals("null")&&f.getTxt_name().equals("null")) {
                f.setTxt_name(txt_name);
                f.setFile_location(file_location);
            }
        }
        else{
            if(f.getFile_location().equals("null")&&f.getTxt_name().equals("null"))
            {
                f.setFile_location(null);
                f.setTxt_name(null);
            }
        }
        f.setFile_updatedate(formatter.format(new Date()));
        fileservice.update(f);
        return result;
    }

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    /**
     * 文档/合同上传，上传文档/合同的字段信息或文件，文件可有可无，若有则需设置文件位置，文件名等字段
     * 需要获取token验证身份信息，每个文档/合同需要对应一个经办人
     */
//    @RequestMapping("/file/upload")
//    public Map<String,Object> uploadFile(@RequestParam(value = "file",required = false) MultipartFile multipartFiles,HttpServletResponse response,HttpServletRequest request,file f)  {
//        String token=request.getHeader("token");
//        Staff s=redisTemplate.opsForValue().get(token);
//        System.out.println("通过了拦截器到达controller先取值:"+s.getStaff_id());
//        int staff_id=s.getStaff_id();
//        Map<String,Object> result=new HashMap<>();
//        //在文件操作中，不用/或者\最好，推荐使用File.separator
//        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
//        String desktopPath = desktopDir.getAbsolutePath();
//        String driname = "files";
//        String rootPath = System.getProperty("user.dir")+ File.separator +driname + File.separator + formatter.format(new Date()) + File.separator;
//
//        try {
//            if (multipartFiles != null)
//            {
//                //String newname=UUID.randomUUID()+"_"+multipartFiles.getOriginalFilename();
//                String newname=UUID.randomUUID().toString().replace("-","")+"_"+multipartFiles.getOriginalFilename();
//                f.setFile_location(URLEncoder.encode(rootPath, "utf-8"));
//                f.setTxt_name(URLEncoder.encode(newname, "utf-8"));
//                File fileDir = new File(rootPath);
//                if (!fileDir.exists() && !fileDir.isDirectory())
//                {
//                    fileDir.mkdirs();
//                }
//                try {
//                    //String extension=multipartFiles[i].getOriginalFilename().substring(multipartFiles[i].getOriginalFilename().lastIndexOf("."));
//                    multipartFiles.transferTo(new File(fileDir,newname));
//                    //String url=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/Springbootfile/"+newname;
//                    result.put("status","success");
//                    result.put("file_loaction",fileDir);
//                    result.put("file_name",newname);
//                } catch (IOException e) {
//                    result.put("status","fail");
//                    result.put("msg",e.getMessage());
//                }
//            }
//            else
//            {
//                result.put("msg","not file");
//            }
//        } catch (Exception e) {
//            result.put("status","fail");
//            result.put("msg",e.getMessage());
//        }
//        f.setFile_uploaddate(formatter.format(new Date()));
//        f.setFile_updatedate(formatter.format(new Date()));
//        f.setJing_ban_ren(staff_id);
//        f.setIf_submit('0');
//        f.setIf_issued('0');
//        f.setIf_delete('0');
//        fileservice.insert(f);
//        return result;
//    }

    @RequestMapping("/file/upload")
    public Map<String,Object> uploadFile(@RequestParam(value = "files",required = false) MultipartFile[] multipartFiles,HttpServletResponse response,HttpServletRequest request,MyFile f)  {
        String token=request.getHeader("token");
        Staff s=redisTemplate.opsForValue().get(token);
        int staff_id=s.getStaff_id();
        Map<String,Object> result=new HashMap<>();
        //在文件操作中，不用/或者\最好，推荐使用File.separator
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        String driname = "files";
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
                System.out.println("压缩完成.");
                //不压缩
//                for(MultipartFile multipartFile :multipartFiles) {
//                    String newname = UUID.randomUUID().toString().replace("-", "") + "_" + multipartFile.getOriginalFilename();
//                    file_location.add(URLEncoder.encode(rootPath, "utf-8"));
//                    txt_name.add(URLEncoder.encode(newname, "utf-8"));
//                    System.out.println(rootPath+newname);
//                    File fileDir = new File(rootPath);
//                    if (!fileDir.exists() && !fileDir.isDirectory()) {
//                        fileDir.mkdirs();
//                    }
//                    try {
//                        multipartFile.transferTo(new File(fileDir, newname));
//                        result.put("status", "success");
//                        newnames.add(newname);
//                    } catch (IOException e) {
//                        result.put("status", "fail");
//                        result.put("msg", e.getMessage());
//                    }
//                }

                //多文件打包压缩存储
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
        f.setFile_location(file_location);
        f.setTxt_name(txt_name);
        f.setFile_uploaddate(formatter.format(new Date()));
        f.setFile_updatedate(formatter.format(new Date()));
        f.setJing_ban_ren(staff_id);
        f.setIf_submit('0');
        f.setIf_issued('0');
        f.setIf_delete('0');
        fileservice.insert(f);
        return result;
    }

    /**
     * 文档文件下载
     * 需要提供文件名及文件的位置
     */
    @RequestMapping("/file/download1")
    public Map<String,Object> downloadFile(@RequestParam String fileName,@RequestParam String fileLocation,HttpServletResponse response,HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String,Object> result=new HashMap<>();
//        ServletOutputStream os = null;
//        InputStream is= null;
        System.out.println(URLDecoder.decode(fileLocation)+URLDecoder.decode(fileName));
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
     * 文档文件删除，若有文件则需要同时删除文件
     */
    @RequestMapping("/file/deletefile")
    public Map<String,Object> deletefile(HttpServletResponse response,HttpServletRequest request,MyFile f)
    {
        Map<String,Object> result=new HashMap<>();
        fileservice.delete(f);
        if(!f.getFile_location().equals("null")&&!f.getTxt_name().equals("null"))
        {

            File file=new File(URLDecoder.decode(f.getFile_location())+URLDecoder.decode(f.getTxt_name()));
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
