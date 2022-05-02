package com.example.demo.service;


import com.example.demo.dao.IUserDao;
import com.example.demo.pojo.User;
import lombok.val;
import org.springframework.stereotype.Service;

/**通过用户界面的用户信息**/
@Service
public class UserInfoService {

    IUserDao iud;
    /**通过用户ID获取用户对应信息**/
    public User getUserinfo(int id)
    {
        val user=iud.getUserById(id);
        return user.get(0);
    }

    /**通过用户ID修改用户对应信息**/
    public String updateUserinfo(User U)
    {
        iud.updateUserById(U);
        return "ok";
    }

}
