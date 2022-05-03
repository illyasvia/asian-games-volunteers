package com.example.demo.controller;

import com.example.demo.pojo.Volunteering;
import com.example.demo.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/act")
public class ActivityController {

    @Autowired
    ActivityService asi;

    @RequestMapping("/query")
    public List<Volunteering> getActivity(@RequestBody Volunteering v)
    {
        return asi.getActivities(v);
    }

    @RequestMapping("/add")
    public int addActivity(@RequestBody Volunteering v)
    {
        return asi.addActivity(v);
    }

    @RequestMapping("/queryone")
    public Volunteering queryone(@RequestBody Volunteering v)
    {
        return asi.getOneActivity(v.getVid());
    }
}
