package com.myproject.api.springboot_mybatis.serviceImpl;

import com.myproject.api.springboot_mybatis.dao.FileMapper;
import com.myproject.api.springboot_mybatis.entity.MyFile;
import com.myproject.api.springboot_mybatis.service.FileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    FileMapper filemapper;

    @Override
    public List<MyFile> QueryFile(MyFile f) {
        return filemapper.QueryFile(f);
    }

    @Override
    public List<MyFile> GetAllFile()
    {
        return filemapper.GetAllFile();
    }

    @Override
    public void insert(MyFile f) {
        filemapper.insert(f);
    }

    @Override
    public List<MyFile> download(MyFile f) {
        return filemapper.download(f);
    }

    @Override
    public List<MyFile> SearchFile(MyFile f) {
        return  filemapper.SearchFile(f);
    }

    @Override
    public void update(MyFile f) {
        filemapper.update(f);
    }

    @Override
    public void delete(MyFile f) {
        filemapper.delete(f);
    }

    @Override
    public List<MyFile> GetAllContract(MyFile f) {
        return filemapper.GetAllContract(f);
    }

    @Override
    public List<MyFile> GetOperator(MyFile f) {
        return filemapper.GetOperator(f);
    }

    @Override
    public List<MyFile> GetChecker(MyFile f) {
        return filemapper.GetChecker(f);
    }

    @Override
    public String GetName(int shen_he_ren) {
        return filemapper.GetName(shen_he_ren);
    }

    @Override
    public void submitfile(MyFile f) {
        filemapper.submitfile(f);
    }

    @Override
    public void checkpass(MyFile f) {
        filemapper.checkpass(f);
    }

    @Override
    public void checknotpass(MyFile f) {
        filemapper.checknotpass(f);
    }

    @Override
    public void checkdelete(MyFile f) {
        filemapper.checkdelete(f);
    }

    @Override
    public List<MyFile> GetAllContractChecker(MyFile f) {
        return filemapper.GetAllContractChecker(f);
    }

    @Override
    public List<MyFile> getAllCheckerFile() {
        return filemapper.getAllCheckerFile();
    }


    @Override
    public List<MyFile> GetAllCon() {
        return filemapper.GetAllCon();
    }
}
