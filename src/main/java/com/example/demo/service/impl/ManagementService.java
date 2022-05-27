package com.example.demo.service.impl;

import com.example.demo.common.InformationException;
import com.example.demo.common.Result;
import com.example.demo.dao.IInforDao;
import com.example.demo.dao.IUserDao;
import com.example.demo.dao.IVolunteeringDao;
import com.example.demo.pojo.User;
import com.example.demo.pojo.Volunteering;
import com.example.demo.service.IManagementService;
import com.example.demo.service.IUserService;
import com.github.pagehelper.PageHelper;
import lombok.Synchronized;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.Line;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
public class ManagementService implements IManagementService {
    @Autowired
    private IInforDao iInforDao;
    @Autowired
    private IVolunteeringDao iVolunteeringDao;
    @Autowired
    private IUserDao iUserDao;
    // 事务管理
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Autowired
    private TransactionDefinition transactionDefinition;


    @Override
    public Result<?> getAll(Integer currentPage, Integer rows) {
        Result<?> result;
        try{
            if(rows > 0) PageHelper.startPage(currentPage,rows);
            result = Result.success(iInforDao.getAll());
            return result;
        } catch (Exception e){
            result = Result.error(Result.UNKNOWN_ERROR, e.getMessage());
        }
        return result;
    }

    @Override
    public Result<?> getInforByUid(Integer uid) {
        return Result.success(iInforDao.getInforByUid(uid));
    }

    @Override
    public Result<?> getInforByVid(Integer vid) {
        return Result.success(iInforDao.getInforByVid(vid));
    }

    @Transactional
    @Override
    public Result<?> deleteSpecifiedInfor(Integer uid, Integer vid) {
        Result<?> result;
        try{
            if (iUserDao.getUserById(uid).size() == 0){
                throw new InformationException("没有该用户");
            }
            if(iVolunteeringDao.getVolById(vid).size() == 0){
                throw new InformationException("没有该活动");
            }
            iInforDao.deleteSpecifiedInfor(uid,vid);
            result = Result.success();
        } catch (InformationException e){
            result = Result.error(Result.INFORMATION_ERROR,e.getMessage());
        } catch (Exception e){
            result = Result.error(Result.UNKNOWN_ERROR, e.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public Result<?> deleteInforByVid(Integer vid) {
        Result<?> result;
        try{
            if(iVolunteeringDao.getVolById(vid).size() == 0){
                throw new InformationException("没有该活动");
            }
            iInforDao.deleteInforByVid(vid);
            result = Result.success();
        } catch (InformationException e){
            result = Result.error(Result.INFORMATION_ERROR,e.getMessage());
        } catch (Exception e){
            result = Result.error(Result.UNKNOWN_ERROR, "未知错误");
        }
        return result;
    }

    // 报名，需要先判断是否人数已满
    @Override
    public synchronized Result<?> registration(Integer uid, Integer vid) {
        // 开启事务
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        Result<?> result;
        try{
            if (iUserDao.getUserById(uid).size() == 0){
                throw new InformationException("没有该用户");
            }

            Integer currentNum = iInforDao.getCountByVid(vid);
            List<Volunteering> vList = iVolunteeringDao.getVolById(vid);
            if(vList.size() == 0){
                throw new InformationException("没有该活动");
            }
            if(iInforDao.getRegistration(uid,vid) == 1){
                throw new InformationException("已报名，请勿重新报名");
            }
            Volunteering v = vList.get(0);
            if(v.getPNum().equals(currentNum)){
                throw new InformationException("人数已满，无法报名");
            }

            iInforDao.addInfor(uid,vid);
            result = Result.success();
            platformTransactionManager.commit(transactionStatus);
            return result;
        } catch (InformationException e){
            result = Result.error(Result.INFORMATION_ERROR,e.getMessage());
            platformTransactionManager.rollback(transactionStatus);
        } catch (Exception e){
            result = Result.error(Result.UNKNOWN_ERROR, e.getMessage());
            platformTransactionManager.rollback(transactionStatus);
        }
        return result;
    }
}