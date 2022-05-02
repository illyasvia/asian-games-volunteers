package com.example.demo.dao;

import com.example.demo.pojo.Volunteering;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 志愿活动dao
 */
@Mapper
public interface IVolunteeringDao {
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
