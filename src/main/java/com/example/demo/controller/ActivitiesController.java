package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.Result;
import com.example.demo.pojo.Volunteering;
import com.example.demo.service.IActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * 志愿者服务相关 controller
 */
@Slf4j
@RestController
@RequestMapping("/volunteering")
public class ActivitiesController {
    @Autowired
    private IActivityService iActivityService;
    @GetMapping
    public Result<?> get(@RequestParam(value = "currentPage", defaultValue = "0") Integer currentPage
            , @RequestParam(value = "rows", defaultValue = "0") Integer rows) {
        return iActivityService.getVolunteering(currentPage, rows);
    }

    @GetMapping("/{vid}")
    public Result<?> get(@PathVariable(name = "vid") Integer vid) {
        return iActivityService.getById(vid);
    }

    @PostMapping
    public Result<?> save(@RequestParam Map<String, Object> map) {
        Volunteering v = (Volunteering) JSON.parseObject(JSON.toJSONString(map), Volunteering.class);
        return iActivityService.addVol(v);
    }

    @PutMapping
    public Result<?> update(@RequestParam Map<String, Object> map) {
        Volunteering v = (Volunteering) JSON.parseObject(JSON.toJSONString(map), Volunteering.class);
        return iActivityService.updateVolById(v);
    }

    @DeleteMapping
    public Result<?> delete(@RequestParam Integer vid) {
        return iActivityService.deleteVol(vid);
    }

    @GetMapping("/search")
    public Result<?> search(@RequestParam(value = "word") String word) {
        return iActivityService.searchByContent(word);
    }

    @GetMapping("/filter")
    public Result<?> filter(@RequestParam(value = "region") int region, @RequestParam(value = "type") int type,
                            @RequestParam(value = "status") int status, @RequestParam(value = "min") int min,
                            @RequestParam(value = "max") int max, @RequestParam(value = "start") String start,@RequestParam(value = "end")String end) {
        java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
        try {
            return iActivityService.Filter(region,type,status,min,max,formatter.parse(start),formatter.parse(end));
        } catch (ParseException e) {
            e.printStackTrace();
            return Result.error(1005,"error argument");
        }
    }
}
