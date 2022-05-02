package com.example.demo.dao;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户信息dao
 */
@Mapper
public interface IUserDao {
    List<User> getAll();

    List<User> getUserById(Integer uid);

    void deleteUserById(Integer uid);

    /**
     * 在id的基础上更改user，对于每一项信息，不为null便更改，为null不更改
     * @param user: 根据此参数的信息修改数据库，user参数必须含有uid
     */
    void updateUserById(User user);

    /**
     * @param user: 增加user,uid为空
     * @return : 返回新加入user的uid
     */
    Integer addUser(User user);
}
