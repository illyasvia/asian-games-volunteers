package com.example.demo.dao;

import com.example.demo.pojo.Condition;
import com.example.demo.pojo.User;
import com.example.demo.pojo.Volunteering;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户 与 报名活动 dao
 * 将用户 与 活动关联起来
 * 该类没有修改类，你报错了你退了活动重报就行，没有改的理由
 * ps:我不知道叫啥好
 */
@Mapper
public interface IInforDao {
    
    /**
     * @return 返回的 User 中带有报名的活动
     */
    List<User> getAll();

    /**
     * 根据用户id获取用户及报名的活动
     * @param uid 用户id
     * @return 返回用户 与 报名的活动
     */
    List<User> getInforByUid(Integer uid);

    /**
     * 根据志愿者活动id获取志愿活动及报名改志愿活动的用户
     * @param vid 志愿者活动的vid
     * @return 返回的志愿者活动中带有 报名该志愿者活动的用户
     */
    List<Volunteering> getInforByVid(Integer vid);

    /**
     * 获取报名的人数
     */
    Integer getCountByVid(Integer vid);

    /**
     * 获取指定的报名信息
     */
    Integer getRegistration(@Param("uid") Integer uid, @Param("vid") Integer vid);

    /**
     * 根据 用户名删除 志愿者活动
     * 就是取消一个用户的某项报名
     */
    void deleteSpecifiedInfor(@Param("uid") Integer uid, @Param("vid") Integer vid);

    /**
     * 删除某项志愿者活动，删除该活动时，应删除所有报名信息
     * @param vid 志愿者活动id
     */
    void deleteInforByVid(Integer vid);

    /**
     * 报名
     */
    void addInfor(@Param("uid") Integer uid, @Param("vid") Integer vid);

    /**
     * 志愿者注销时，应删除所有报名信息
     * @param uid 志愿者id
     */
    void deleteInforByUid(Integer uid);
}
