package com.myproject.api.springboot_mybatis.controller;

import com.myproject.api.springboot_mybatis.entity.Staff;
import com.myproject.api.springboot_mybatis.error.BusinessException;
import com.myproject.api.springboot_mybatis.error.EmBusinessError;
import com.myproject.api.springboot_mybatis.response.CommonReturnType;
import com.myproject.api.springboot_mybatis.service.staffService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
public class staffController extends BaseController{
    @Autowired
    staffService staffService;

    @Resource
    RedisTemplate<String, Staff> redisTemplate;


    //用户登录模块
    @CrossOrigin
    @RequestMapping(value = "/staff/login",method = {RequestMethod.POST},consumes = {})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name="account") String account,
                                  @RequestParam(name="password") String password) throws BusinessException {

        //入参校验
        if(StringUtils.isEmpty(account)||
                StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //用户登录服务，校验用户登录是否合法
        Staff staff = staffService.validateLogin(account,password);

        //存入redis中
        String uuidToken = UUID.randomUUID().toString().replace("-","");
        //建立token和用户登录态之间的联系
        redisTemplate.opsForValue().set(uuidToken,staff);
        redisTemplate.expire(uuidToken,1, TimeUnit.HOURS);
//        Staff s=redisTemplate.opsForValue().get("d4d04043c407436a8e848af7c6eac601");
//        System.out.println(s.toString());
        //将登录凭证加入到用户登录成功的session内
//        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
//        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);

        return CommonReturnType.create(uuidToken,staff);

    }

    @CrossOrigin
    @GetMapping(value = "/staff/getAllstaff")
    public List<Staff> getAllstaff(){
        return staffService.getAllstaff();
    }

    @ResponseBody
    @CrossOrigin
    @RequestMapping(value = "/stuff/delete/load")
    public List<Map<String, Object>> ReadAllClient() throws JSONException {
        List<Staff> staffList = staffService.readAllStaff();
        if(staffList.size()==0){
            return null;
        }
        List<Map<String, Object>> mapList = new ArrayList<>();
        for(Staff s : staffList){
            Map<String,Object> resultMap  = new HashMap<String, Object>();
            resultMap.put("Id",s.getStaff_id());
            resultMap.put("name",s.getStaff_name());
            resultMap.put("sex",s.getStaff_sex());
            resultMap.put("number",s.getStaff_phone());
            resultMap.put("job",s.getStaff_job());
            resultMap.put("point",0);
            resultMap.put("score",0);
            resultMap.put("salary",s.getStaff_wage());
            resultMap.put("weight",s.getStaff_permission());
            mapList.add(resultMap);
        }
        return mapList;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/stuff/add")
    public String InsertStaff(@Param("Id") String Id,@Param("name") String name, @Param ("sex") String sex,
                              @Param("number") String number,@Param("job") String job,@Param("point")String point,
                              @Param("score")String score,@Param("salary") String salary,@Param("weight") String weight) throws JSONException {
        Staff s = new Staff();
        s.setStaff_account(Id);
        s.setStaff_name(name);
        s.setStaff_sex(sex);
        s.setStaff_phone(number);
        s.setStaff_job(job);
        s.setStaff_wage(salary);
        s.setStaff_permission(weight);
        int result = staffService.insertStaff(s);
        JSONObject object = new JSONObject();
        if(result==1){
            object.put("status","success");
        }else{
            object.put("status","error");
        }
        return object.toString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/stuff/update")
    public String updateStaff(@Param("Id") Integer Id,@Param("name") String name, @Param ("sex") String sex,
                              @Param("number") String number,@Param("job") String job,@Param("point")String point,
                              @Param("score")String score,@Param("salary") String salary,@Param("weight") String weight) throws JSONException{
        int result = staffService.updateStaff(Id,name,sex,number,job,salary,weight);
        JSONObject object = new JSONObject();
        if(result == 1){
            object.put("status","success");
        }else{
            object.put("status","error");
        }
        System.out.println(object.toString());
        return object.toString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value="/stuff/delete")
    public String deleteStaff(@Param("Id") Integer Id) throws JSONException {
        int result = staffService.deleteStaff(Id);
        JSONObject object = new JSONObject();
        if(result == 1){
            object.put("status","success");
        }else{
            object.put("status","error");
        }
        return object.toString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/staff/info")
    public Staff staffInfoById(@Param("staff_id") Integer staff_id) throws JSONException{
        System.out.println(staff_id);   
        Staff staff = staffService.staffInfoById(staff_id);
        String msg = "";
        if(staff==null){
            msg="No such Staff";
        }else{
            msg="Get A Staff";
        }
        return staff;
    }

    @CrossOrigin
    @PostMapping(value="/staff/update")
    public String staffInfoUpdate(Staff staff) throws JSONException{
        int result = staffService.updateStaffInfo(staff);
        JSONObject object = new JSONObject();
        if(result == 1){
            object.put("status","success");
        }else{
            object.put("status","error");
        }
        return object.toString();
    }

}