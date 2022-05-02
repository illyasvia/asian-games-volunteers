package com.example.demo.controller;

import com.example.demo.pojo.User;
import com.example.demo.service.ActivityService;
import com.example.demo.service.UserInfoService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class UserController {
    @Autowired
    UserInfoService uis;

    @GetMapping("/query/{uid}")
    public User query(@PathVariable int uid) {
        return uis.getUserinfo(uid);
    }

    @PostMapping("/add")
    public int query(@RequestBody User user) {
        return uis.register(user);
    }

    @PostMapping("/update")
    public int update(@RequestBody User user) {
        return uis.updateUserinfo(user);
    }

    @PostMapping("/login")
    public int login(@RequestBody User user) {
        return uis.log(user.getUid(), user.getPassword());
    }


}

