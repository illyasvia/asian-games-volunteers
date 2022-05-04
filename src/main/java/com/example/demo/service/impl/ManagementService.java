package com.example.demo.service.impl;

import com.example.demo.common.Result;
import com.example.demo.dao.IInforDao;
import com.example.demo.dao.IVolunteeringDao;
import com.example.demo.pojo.Volunteering;
import com.example.demo.service.IManagementService;
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

import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
public class ManagementService implements IManagementService {
    @Autowired
    private IInforDao iInforDao;
    @Autowired
    private IVolunteeringDao iVolunteeringDao;
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
        Result<?> result;
        try{
            result = Result.success(iInforDao.getInforByUid(uid));
            return result;
        } catch (DataAccessException e){
            result = Result.error(Result.INFORMATION_ERROR,"传入参数未知");
        } catch (Exception e){
            result = Result.error(Result.UNKNOWN_ERROR, e.getMessage());
        }
        return result;
    }

    @Override
    public Result<?> getInforByVid(Integer vid) {
        Result<?> result;
        try{
            result = Result.success(iInforDao.getInforByUid(vid));
            return result;
        } catch (DataAccessException e){
            result = Result.error(Result.INFORMATION_ERROR,"传入参数未知");
        } catch (Exception e){
            result = Result.error(Result.UNKNOWN_ERROR, e.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public Result<?> deleteSpecifiedInfor(Integer uid, Integer vid) {
        Result<?> result;
        try{
            iInforDao.deleteSpecifiedInfor(uid,vid);
            result = Result.success();
            return result;
        } catch (DataAccessException e){
            result = Result.error(Result.INFORMATION_ERROR,"信息错误");
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
            iInforDao.deleteInforByVid(vid);
            result = Result.success();
            return result;
        } catch (DataAccessException e){
            result = Result.error(Result.INFORMATION_ERROR,"信息错误");
        } catch (Exception e){
            result = Result.error(Result.UNKNOWN_ERROR, e.getMessage());
        }
        return result;
    }

    // 报名，需要先判断是否人数已满
    @Override
    public synchronized Result<?> registration(Integer uid, Integer vid) {
        // 开启事务
        log.info("uid="+uid+"我开始执行啦！");
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        Result<?> result;
        try{
            Integer currentNum = iInforDao.getCountByVid(vid);
            Volunteering v = iVolunteeringDao.getVolById(vid).get(0);
            if(v.getPNum().equals(currentNum)){
                result = Result.error(Result.FULL_REGISTRATION,"人数已满，无法报名");
            } else {
                iInforDao.addInfor(uid,vid);
                result = Result.success();
            }
            return result;
        } catch (DataAccessException e){
            result = Result.error(Result.INFORMATION_ERROR,"信息错误");
        } catch (Exception e){
            result = Result.error(Result.UNKNOWN_ERROR, e.getMessage());
        } finally {
            platformTransactionManager.commit(transactionStatus);
            log.info("uid="+uid+"执行完毕了！！！！");
        }
        return result;
    }
}