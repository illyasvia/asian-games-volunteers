package com.example.demo.controller;

import com.example.demo.dao.IInforDao;
import com.example.demo.dao.IUserDao;
import com.example.demo.dao.IVolunteeringDao;
import com.example.demo.pojo.User;
import com.example.demo.common.Result;
import com.example.demo.pojo.Volunteering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class MyController {
    @Autowired
    private IInforDao iInforDao;

    @PostMapping
    public Result<User> post(User user){
        System.out.println(user);
        return Result.success(user);
    }
    @GetMapping
    public List<User> get(){
        return iInforDao.getInforByUid(78);
    }
}
