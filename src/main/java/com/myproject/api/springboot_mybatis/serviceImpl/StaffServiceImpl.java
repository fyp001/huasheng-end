package com.myproject.api.springboot_mybatis.serviceImpl;

import com.myproject.api.springboot_mybatis.dao.StaffDao;
import com.myproject.api.springboot_mybatis.entity.Staff;
import com.myproject.api.springboot_mybatis.error.BusinessException;
import com.myproject.api.springboot_mybatis.error.EmBusinessError;
import com.myproject.api.springboot_mybatis.service.StaffService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    @Resource
    StaffDao staffDao;

    @Override
    public Staff validateLogin(String account, String password) throws BusinessException {
        //通过账号获取用户信息
        Staff staff = staffDao.selectByAccount(account);
        if(staff == null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }

        //比对用户信息内加密的密码是否和传输进来的密码相匹配
        //传过来的密码是加密过的，和从userModel中的进行比对
        if( !password.equals(staff.getStaff_password() ) ){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }

        return staff;
    }

    @Override
    public List<Staff> getAllstaff() {
        return staffDao.getAllstaff();
    }

    @Override
    public int deleteStaff(int staff_id) {
        return staffDao.deleteStaff(staff_id);
    }

    @Override
    public int insertStaff(Staff staff) {
        return staffDao.insertStaff(staff);
    }

    @Override
    public int updateStaff(int staff_id, String staff_name, String staff_sex, String staff_phone, String staff_job, String staff_wage, String staff_permission) {
        return staffDao.updateStaff(staff_id,staff_name,staff_sex,staff_phone,staff_job,staff_wage,staff_permission);
    }

    @Override
    public List<Staff> readAllStaff() {
        return staffDao.readAllStaff();
    }

    @Override
    public Staff staffInfoById(Integer staff_id) {
        return staffDao.staffInfoById(staff_id);
    }

    @Override
    public int updateStaffInfo(Staff staff) {
        return staffDao.updateStaffInfo(staff);
    }


}
