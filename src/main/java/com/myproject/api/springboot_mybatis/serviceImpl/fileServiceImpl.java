package com.myproject.api.springboot_mybatis.serviceImpl;

import com.myproject.api.springboot_mybatis.dao.fileMapper;
import com.myproject.api.springboot_mybatis.entity.file;
import com.myproject.api.springboot_mybatis.service.fileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class fileServiceImpl implements fileService {
    @Resource
    fileMapper filemapper;

    @Override
    public List<file> QueryFile(file f) {
        return filemapper.QueryFile(f);
    }

    @Override
    public List<file> GetAllFile()
    {
        return filemapper.GetAllFile();
    }

    @Override
    public void insert(file f) {
        filemapper.insert(f);
    }

    @Override
    public List<file> download(file f) {
        return filemapper.download(f);
    }

    @Override
    public List<file> SearchFile(file f) {
        return  filemapper.SearchFile(f);
    }

    @Override
    public void update(file f) {
        filemapper.update(f);
    }

    @Override
    public void delete(file f) {
        filemapper.delete(f);
    }

    @Override
    public List<file> GetAllContract(file f) {
        return filemapper.GetAllContract(f);
    }

    @Override
    public List<file> GetOperator(file f) {
        return filemapper.GetOperator(f);
    }

    @Override
    public List<file> GetChecker() {
        return filemapper.GetChecker();
    }

    @Override
    public String GetName(int shen_he_ren) {
        return filemapper.GetName(shen_he_ren);
    }

    @Override
    public void submitfile(file f) {
        filemapper.submitfile(f);
    }

    @Override
    public void checkpass(file f) {
        filemapper.checkpass(f);
    }

    @Override
    public void checknotpass(file f) {
        filemapper.checknotpass(f);
    }

    @Override
    public void checkdelete(file f) {
        filemapper.checkdelete(f);
    }

    @Override
    public List<file> GetAllContractChecker() {
        return filemapper.GetAllContractChecker();
    }
}
