package com.myproject.api.springboot_mybatis.service;

import com.myproject.api.springboot_mybatis.entity.MyFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileService {
    List<MyFile> QueryFile(MyFile f);
    void insert(MyFile f);
    List<MyFile> download(MyFile f);
    List<MyFile> SearchFile(MyFile f);
    List<MyFile> GetAllFile();
    void update(MyFile f);
    void delete(MyFile f);
    List<MyFile> GetAllContract(MyFile f);
    List<MyFile> GetOperator(MyFile f);
    List<MyFile> GetChecker(MyFile f);
    List<MyFile> GetAllContractChecker(MyFile f);
    String GetName(int shen_he_ren);
    void submitfile(MyFile f);
    void checkpass(MyFile f);
    void checknotpass(MyFile f);
    void checkdelete(MyFile f);
    List<MyFile> getAllCheckerFile();
    List<MyFile> GetAllCon();
}
