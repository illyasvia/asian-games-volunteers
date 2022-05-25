package com.example.demo.service.impl;

import com.example.demo.common.Result;
import com.example.demo.dao.IInforDao;
import com.example.demo.dao.IUserDao;
import com.example.demo.pojo.User;
import com.example.demo.service.IUserService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;

/**
 * 用户服务service
 * 负责管理用户、添加、删除用户
 */
@Slf4j
@Service
public class UserService implements IUserService {
    @Autowired
    private IUserDao iUserDao;
    @Autowired
    private IInforDao iInforDao;

    @Override
    public Result<?> getAll() {
        return Result.success(iUserDao.getAll());
    }

    /**
     * 按页查询
     *
     * @param currentPage 当前页数
     * @param rows        每页数量
     * @return 返回数据是 用户的List
     */
    @Override
    public Result<?> getByPage(Integer currentPage, Integer rows) {
        PageHelper.startPage(currentPage, rows);
        return Result.success(iUserDao.getAll());
    }

    @Override
    public Result<?> getUser(Integer currentPage, Integer rows) {
        Result<?> result;
        if (rows <= 0) result = getAll();
        else result = getByPage(currentPage, rows);
        return result;
    }

    @Override
    public Result<?> getById(Integer uid) {
        return Result.success(iUserDao.getUserById(uid));
    }

    @Transactional
    @Override
    public Result<?> addUser(User user) {
        user.setRegister(new Date());
        iUserDao.addUser(user);
        return Result.success();
    }

    @Transactional
    @Override
    public Result<?> updateUserById(User user) {
        iUserDao.updateUserById(user);
        return Result.success();
    }

    @Transactional
    @Override
    public Result<?> deleteUser(Integer uid) {
        Result<?> result;
        try {
            iInforDao.deleteInforByUid(uid);
            iUserDao.deleteUserById(uid);
            result = Result.success();
        } catch (Exception e) {
            result = Result.error(Result.UNKNOWN_ERROR, e.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public Result<?> login(String phone, String password) {
        Result<?> result;
        try {
            val array=iUserDao.getAll();
            int flag=0;
            for(val x:array)
            {
                if(x.getPhone().equals(phone))
                {
                    flag++;
                    if(!x.getPassword().equals(password))throw new Exception("password error");
                }
            }
            if(flag==0)throw new Exception("No such USER");
            if(flag==2)throw new Exception("error in database");
            result=Result.success();
        } catch (Exception e) {
            result = Result.error(Result.UNKNOWN_ERROR, e.getMessage());
        }
        return result;
    }

    @Override
    public Result<?> getImage(int uid) {
        val user=iUserDao.getUserById(uid).get(0);
        val url=user.getProfile();
        return  Result.success("http://124.222.202.195:8080/img/"+url);
    }

    @Override
    public Result<?> updateImage(int uid, String url) {
        val user=iUserDao.getUserById(uid).get(0);
        user.setProfile(url);
        iUserDao.updateUserById(user);
        return  Result.success();
    }
}
