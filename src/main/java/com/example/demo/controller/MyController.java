package com.example.demo.controller;

import com.example.demo.Pojo.User;
import com.example.demo.common.Result;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class MyController {
    @PostMapping
    public Result<User> post(User user){
        System.out.println(user);
        return Result.success(user);
    }
    @GetMapping
    public String get(){
        return "xixi";
    }
}
