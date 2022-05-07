package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.example.demo.common.Result;
import com.example.demo.common.UploadImage;
import com.example.demo.pojo.User;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @GetMapping
    public Result<?> get(@RequestParam(value = "currentPage", defaultValue = "0") Integer currentPage
            , @RequestParam(value = "rows", defaultValue = "0") Integer rows) {
        return iUserService.getUser(currentPage, rows);
    }

    @GetMapping("/{uid}")
    public Result<?> get(@PathVariable(name = "uid") Integer uid) {
        return iUserService.getById(uid);
    }

    @PostMapping
    public Result<?> save(@RequestParam Map<String, Object> map) {
        User user = (User) JSON.parseObject(JSON.toJSONString(map), User.class);
        return iUserService.addUser(user);
    }

    @PostMapping("/add")
    public Result<?> add(@RequestBody User v) {
        return iUserService.addUser(v);
    }

    @PutMapping
    public Result<?> update(@RequestParam Map<String, Object> map) {
        User user = (User) JSON.parseObject(JSON.toJSONString(map), User.class);
        return iUserService.updateUserById(user);
    }

    @DeleteMapping
    public Result<?> delete(@RequestParam Integer uid) {
        return iUserService.deleteUser(uid);
    }

    @PostMapping("/login")
    public Result<?> login(@RequestParam String phone, String password) {
        return iUserService.login(phone, password);
    }

    @PostMapping("/upload")
    public Result<?>  uploadImage(@RequestParam(value = "file") MultipartFile multipartFile,@RequestParam(value = "uid") Integer uid) {
        String imagePath = UploadImage.upload(multipartFile);
        return iUserService.updateImage(uid,imagePath);
    }

    @GetMapping("/image")
    public  Result<?> getImage(@RequestParam(value = "uid") Integer uid)
    {
        return iUserService.getImage(uid);
    }


}
