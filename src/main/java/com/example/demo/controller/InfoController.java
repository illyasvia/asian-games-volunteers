package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.service.IManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/info")
public class InfoController {
    @Autowired
    private IManagementService iManagementService;

    @GetMapping()
    public Result<?> getAll(@RequestParam(value = "currentPage",defaultValue = "0") Integer currentPage,
                            @RequestParam(value = "rows", defaultValue = "0") Integer rows){
        return iManagementService.getAll(currentPage,rows);
    }

    @GetMapping("/user/{uid}")
    public Result<?> getInforByUid(@PathVariable(name = "uid") Integer uid){
        return iManagementService.getInforByUid(uid);
    }

    @GetMapping("/volunteering/{vid}")
    public Result<?> getInforByVid(@PathVariable(name = "vid") Integer vid){
        return iManagementService.getInforByVid(vid);
    }

    @PostMapping()
    public Result<?> save(@RequestParam Integer uid, @RequestParam Integer vid){
        return iManagementService.addInfor(uid,vid);
    }

    /**
     * 取消所有报名的人，并不能用于删除活动
     */
    @DeleteMapping()
    public Result<?> deleteByVid(@PathVariable Integer vid){
        return iManagementService.deleteInforByVid(vid);
    }
}
