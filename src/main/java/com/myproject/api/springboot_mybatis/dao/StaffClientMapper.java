package com.myproject.api.springboot_mybatis.dao;


import java.util.List;

import com.myproject.api.springboot_mybatis.entity.StaffClient;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface StaffClientMapper {
    StaffClient Sel(int id);
    List<StaffClient> SelAll();
}
