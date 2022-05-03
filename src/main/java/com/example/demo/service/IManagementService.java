package com.example.demo.service;


import com.example.demo.common.Result;

public interface IManagementService {
    Result<?> getAll(Integer currentPage, Integer rows);
    Result<?> getInforByUid(Integer uid);
    Result<?> getInforByVid(Integer vid);
    Result<?> deleteSpecifiedInfor(Integer uid, Integer vid);
    Result<?> deleteInforByVid(Integer vid);
    Result<?> addInfor(Integer uid, Integer vid);
}
