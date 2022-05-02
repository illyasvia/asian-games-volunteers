package com.example.demo.service;


import com.example.demo.dao.IUserDao;
import com.example.demo.pojo.User;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * 通过用户界面的用户信息
 **/
@Service
public class UserInfoService {
    private static final int SUCCESS = 1;
    private static final int FAIL = 0;

    @Autowired
    IUserDao iud;

    /**
     * 通过用户ID获取用户对应信息
     **/
    public User getUserinfo(int id) {
        List<User> user = iud.getUserById(id);
        return user.get(0);
    }

    /**
     * 通过用户ID修改用户对应信息
     **/
    public int updateUserinfo(User U) {
        try{
            iud.updateUserById(U);
        }catch (Exception e)
        {
            System.out.println(e.fillInStackTrace());
            return FAIL;
        }
        return SUCCESS;
    }

    /**用户登录**/
    public int log(int id,String password) {
        try{
            User user=iud.getUserById(id).get(0);
            if(!user.getPassword().equals(password))return FAIL;
        }catch (Exception e)
        {
            System.out.println(e.fillInStackTrace());
            return FAIL;
        }
        return SUCCESS;
    }

    /**用户注册**/
    public int register(User user) {
        user.setRegister(new Date());
        try{
            iud.addUser(user);
        }catch (Exception e)
        {
            System.out.println(e.fillInStackTrace());
            return FAIL;
        }
        return SUCCESS;
    }

}
