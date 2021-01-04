package com.myproject.api.springboot_mybatis.dao;

import com.myproject.api.springboot_mybatis.entity.Project;
import com.myproject.api.springboot_mybatis.entity.Staff;
import com.myproject.api.springboot_mybatis.entity.Tender;

import java.util.List;

public interface TenderDao {
    List<Tender> getAllTender ();
    List<Tender> getTenderByCurrentRole ();
    void insert(Tender tender);
    void delete(Tender tender);
    void update(Tender tender);
    void submitTender(Tender tender);
    List<Tender> getCheckerTender();
    void checkpassTender(Tender tender);
    void checknotpassTender(Tender tender);
    List <Staff>  getname();
    List<Tender> getCheckTender(Tender tender);
    void pass(Tender tender);
    void refuse(Tender tender);
    void if_delete(Tender tender);
    String GetName(int shen_he_ren);
}
