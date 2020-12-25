package com.myproject.api.springboot_mybatis.serviceImpl;


import com.myproject.api.springboot_mybatis.dao.StaffClientMapper;
import com.myproject.api.springboot_mybatis.entity.StaffClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffClientService {
    @Autowired
    StaffClientMapper staffClientMapper;

    public StaffClient Sel(int c_s_id) {
        return staffClientMapper.Sel(c_s_id);
    }

    public List<StaffClient> SelAll(){
        return staffClientMapper.SelAll();
    }
}

