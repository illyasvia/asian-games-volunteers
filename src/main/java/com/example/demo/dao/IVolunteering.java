package com.example.demo.dao;

import com.example.demo.Pojo.Volunteering;

import java.util.List;

/**
 * 志愿活动dao
 */
public interface IVolunteering {
    List<Volunteering> getAll();

    List<Volunteering> getVolById(Integer vid);

    /**
     * 添加志愿者活动
     */
    void addVol(Volunteering v);

    /**
     * 根据志愿者活动id修改信息
     */
    void updateVolById(Integer vid);

    void deleteVol();
}
