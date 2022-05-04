package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.pojo.User;
import com.example.demo.common.Result;
import com.example.demo.pojo.Volunteering;
import com.example.demo.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 活动服务相关 controller
 */
@RestController
@RequestMapping("/volunteering")
public class ActivitiesController {
    @Autowired
    private IActivityService iActivityService;

    @GetMapping
    public Result<?> get(@RequestParam(value="currentPage",defaultValue="0") Integer currentPage
            ,@RequestParam(value="rows",defaultValue="0") Integer rows){
        return iActivityService.getVolunteering(currentPage,rows);
    }
    @GetMapping("/{vid}")
    public Result<?> get(@PathVariable(name="vid") Integer vid){
        return iActivityService.getById(vid);
    }

    @PostMapping
    public Result<?> save(@RequestParam Map<String,Object> map){
        Volunteering v = (Volunteering) JSON.parseObject(JSON.toJSONString(map),Volunteering.class);
        return iActivityService.addVol(v);
    }

    @PostMapping("/add")
    public Result<?> add(@RequestBody Volunteering v){
        return iActivityService.addVol(v);
    }

    @PutMapping
    public Result<?> update(@RequestParam Map<String,Object> map){
        Volunteering v = (Volunteering) JSON.parseObject(JSON.toJSONString(map),Volunteering.class);
        return iActivityService.updateVolById(v);
    }

    @DeleteMapping
    public Result<?> delete(@RequestParam Integer vid){
        return iActivityService.deleteVol(vid);
    }
}
