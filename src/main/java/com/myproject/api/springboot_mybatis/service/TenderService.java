package com.myproject.api.springboot_mybatis.service;

import com.myproject.api.springboot_mybatis.entity.Project;
import com.myproject.api.springboot_mybatis.entity.Staff;
import com.myproject.api.springboot_mybatis.entity.Tender;

import java.util.List;

public interface TenderService {
    List<Tender>getAllTender ();
    void insert(Tender tender);
    void delete(Tender tender);
    void update(Tender tender);
    List<Tender>getTenderByCurrentRole();
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
