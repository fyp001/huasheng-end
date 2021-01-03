package com.myproject.api.springboot_mybatis.serviceImpl;

import com.myproject.api.springboot_mybatis.dao.ProjectDao;
import com.myproject.api.springboot_mybatis.dao.TenderDao;
import com.myproject.api.springboot_mybatis.entity.Staff;
import com.myproject.api.springboot_mybatis.entity.Tender;
import com.myproject.api.springboot_mybatis.service.TenderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TenderServiceImpl implements TenderService {
    @Resource
    TenderDao tenderDao;

    @Override
    public List<Tender> getAllTender() {
        return tenderDao.getAllTender();
    }

    @Override
    public void insert(Tender tender) {
        tenderDao.insert(tender);
    }

    @Override
    public void delete(Tender tender) {
        tenderDao.delete(tender);
    }

    @Override
    public void update(Tender tender) {
        tenderDao.update(tender);
    }

    @Override
    public List<Tender> getTenderByCurrentRole() {
        return tenderDao.getTenderByCurrentRole();
    }

    @Override
    public void submitTender(Tender tender) {
        tenderDao.submitTender(tender);
    }

    @Override
    public List<Tender> getCheckerTender() {
        return tenderDao.getCheckerTender();
    }

    @Override
    public void checkpassTender(Tender tender) {
        tenderDao.checkpassTender(tender);
    }

    @Override
    public void checknotpassTender(Tender tender) {
        tenderDao.checknotpassTender(tender);
    }

    @Override
    public List<Staff> getname() {
        return tenderDao.getname();
    }

    @Override
    public List<Tender> getCheckTender(Tender tender) {
        return tenderDao.getCheckTender(tender);
    }

    @Override
    public void pass(Tender tender) {
        tenderDao.pass(tender);
    }

    @Override
    public void refuse(Tender tender) {
        tenderDao.refuse(tender);
    }

    @Override
    public void if_delete(Tender tender) {
        tenderDao.if_delete(tender);
    }
}
