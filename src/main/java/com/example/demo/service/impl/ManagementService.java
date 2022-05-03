package com.example.demo.service.impl;

import com.example.demo.common.Result;
import com.example.demo.dao.IInforDao;
import com.example.demo.service.IManagementService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagementService implements IManagementService {
    @Autowired
    private IInforDao iInforDao;
    @Override
    public Result<?> getAll(Integer currentPage, Integer rows) {
        if(rows > 0) PageHelper.startPage(currentPage,rows);
        return Result.success(iInforDao.getAll());
    }

    @Override
    public Result<?> getInforByUid(Integer uid) {
        return Result.success(iInforDao.getInforByUid(uid));
    }

    @Override
    public Result<?> getInforByVid(Integer vid) {
        return Result.success(iInforDao.getInforByVid(vid));
    }

    @Override
    public Result<?> deleteSpecifiedInfor(Integer uid, Integer vid) {
        iInforDao.deleteSpecifiedInfor(uid,vid);
        return Result.success();
    }

    @Override
    public Result<?> deleteInforByVid(Integer vid) {
        iInforDao.deleteInforByVid(vid);
        return Result.success();
    }

    @Override
    public Result<?> addInfor(Integer uid, Integer vid) {
        iInforDao.addInfor(uid,vid);
        return Result.success();
    }
}
