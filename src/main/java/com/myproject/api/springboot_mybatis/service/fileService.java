package com.myproject.api.springboot_mybatis.service;

import com.myproject.api.springboot_mybatis.entity.file;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface fileService {
    List<file> QueryFile(file f);
    void insert(file f);
    List<file> download(file f);
    List<file> SearchFile(file f);
    List<file> GetAllFile();
    void update(file f);
    void delete(file f);
    List<file> GetAllContract(file f);
    List<file> GetOperator(file f);
    List<file> GetChecker();
    List<file> GetAllContractChecker();
    String GetName(int shen_he_ren);
    void submitfile(file f);
    void checkpass(file f);
    void checknotpass(file f);
    void checkdelete(file f);

}
