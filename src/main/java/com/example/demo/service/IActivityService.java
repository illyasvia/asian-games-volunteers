package com.example.demo.service;

import com.example.demo.common.Result;
import com.example.demo.pojo.Volunteering;

/**
 * ClassName: IActivityService
 * Description:
 * date: 2022/5/3 14:25
 *
 * @author zyu
 * @since JDK 1.8
 */
public interface IActivityService {
    Result<?> getAll();
    Result<?> getByPage(Integer currentPage,Integer rows);
    Result<?> getVolunteering(Integer currentPage, Integer rows);
    Result<?> getById(Integer vid);
    Result<?> addVol(Volunteering v);
    Result<?> updateVolById(Volunteering v);
    Result<?> deleteVol(Integer vid);
    Result<?>searchByContent(String word);
}
