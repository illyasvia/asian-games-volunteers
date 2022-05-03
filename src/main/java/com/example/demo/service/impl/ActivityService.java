package com.example.demo.service.impl;

import com.example.demo.common.Result;
import com.example.demo.dao.IInforDao;
import com.example.demo.dao.IVolunteeringDao;
import com.example.demo.pojo.Volunteering;
import com.example.demo.service.IActivityService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 活动服务service
 * 负责管理活动、添加、删除、发布活动
 */
@Service
public class ActivityService implements IActivityService {
    @Autowired
    private IVolunteeringDao iVolunteeringDao;
    @Autowired
    private IInforDao iInforDao;

    @Override
    public Result<?> getAll(){
        return Result.success(iVolunteeringDao.getAll());
    }

    /**
     * 按页查询
     * @param currentPage 当前页数
     * @param rows 每页数量
     * @return 返回数据是 活动的List
     */
    @Override
    public Result<?> getByPage(Integer currentPage, Integer rows){
        PageHelper.startPage(currentPage,rows);
        return Result.success(iVolunteeringDao.getAll());
    }

    @Override
    public Result<?> getVolunteering(Integer currentPage, Integer rows){
        Result<?> result;
        if(rows <= 0) result = getAll();
        else result = getByPage(currentPage,rows);
        return result;
    }

    @Override
    public Result<?> getById(Integer vid) {
        return Result.success(iVolunteeringDao.getVolById(vid));
    }

    @Override
    public Result<?> addVol(Volunteering v) {
        iVolunteeringDao.addVol(v);
        return Result.success();
    }

    @Override
    public Result<?> updateVolById(Volunteering v) {
        iVolunteeringDao.updateVolById(v);
        return Result.success();
    }

    @Override
    public Result<?> deleteVol(Integer vid) {
        // 需要先删除报名该活动的人的信息
        iInforDao.deleteInforByVid(vid);
        iVolunteeringDao.deleteVol(vid);
        return Result.success();
    }
}
