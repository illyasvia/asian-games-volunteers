package com.example.demo.service;

import com.example.demo.common.Result;
import com.example.demo.pojo.User;

public interface IUserService {
    Result<?> getAll();
    Result<?> getByPage(Integer currentPage,Integer rows);
    Result<?> getUser(Integer currentPage, Integer rows);
    Result<?> getById(Integer uid);
    Result<?> addUser(User user);
    Result<?> updateUserById(User user);
    Result<?> deleteUser(Integer uid);
    Result<?> login(String phone,String password);

}

