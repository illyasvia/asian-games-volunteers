package com.example.demo.dao;

import com.example.demo.pojo.VolCondition;
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

    List<Volunteering> getByCondition(VolCondition condition);

    /**
     * 添加志愿者活动
     */
    void addVol(Volunteering volunteering);

    /**
     * 根据志愿者活动id修改信息
     */
    void updateVolById(Volunteering volunteering);

    void deleteVol(Integer vid);
}
