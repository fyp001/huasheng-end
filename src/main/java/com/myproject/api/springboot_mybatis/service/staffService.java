package com.myproject.api.springboot_mybatis.service;

import com.myproject.api.springboot_mybatis.entity.Staff;
import com.myproject.api.springboot_mybatis.error.BusinessException;

import java.util.List;

public interface staffService {
    List<Staff> getAllstaff();

    int deleteStaff(int staff_id);

    int insertStaff(Staff staff);

    int updateStaff(int staff_id,String staff_name,String staff_sex,String staff_phone,
                    String staff_job,String staff_wage,String staff_permission);

    List<Staff> readAllStaff();

    Staff staffInfoById(Integer staff_id);

    int updateStaffInfo(Staff staff);

    Staff validateLogin(String account, String password) throws BusinessException;


}
