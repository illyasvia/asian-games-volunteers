package com.example.demo.dao;

import com.example.demo.Pojo.Volunteering;

import java.util.List;

/**
 * 志愿活动dao
 */
public interface IVolunteering {
    List<Volunteering> getAll();
    List<Volunteering> getVolById(Integer vid);
    void deleteVol();
}
