package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.Result;
import com.example.demo.common.UploadImage;
import com.example.demo.pojo.VolCondition;
import com.example.demo.pojo.Volunteering;
import com.example.demo.service.IActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public Result<?> delete(@RequestParam(name = "vid") Integer vid) {
        return iActivityService.deleteVol(vid);
    }

    @GetMapping("/search")
    public Result<?> search(@RequestParam(value = "word") String word) {
        return iActivityService.searchByContent(word);
    }

    @GetMapping("/filter")
    public Result<?> filter(@RequestParam Map<String,Object> map) {
        VolCondition condition = JSON.parseObject(JSON.toJSONString(map), VolCondition.class);
        return iActivityService.Filter(condition);
    }

    @PostMapping("/upload")
    public Result<?>  uploadImage(@RequestParam(value = "file") MultipartFile multipartFile,@RequestParam(value = "vid") Integer vid) {
        String imagePath = UploadImage.upload(multipartFile);
        return iActivityService.updateImage(vid,imagePath);
    }
}
