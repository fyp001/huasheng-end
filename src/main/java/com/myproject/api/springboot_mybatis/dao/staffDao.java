package com.myproject.api.springboot_mybatis.dao;

import com.myproject.api.springboot_mybatis.entity.Staff;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface staffDao {
    List<Staff> getAllstaff();

    int deleteStaff(int staff_id);

    int insertStaff(Staff staff);

    int updateStaff(int staff_id,String staff_name,String staff_sex,String staff_phone,
                    String staff_job,String staff_wage,String staff_permission);

    List<Staff> readAllStaff();

    Staff staffInfoById(Integer staff_id);

    int updateStaffInfo(Staff staff);

    Staff selectByAccount(String account);

}
