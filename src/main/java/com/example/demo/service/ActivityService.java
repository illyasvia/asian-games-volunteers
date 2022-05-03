package com.example.demo.service;

import com.example.demo.dao.IVolunteeringDao;
import com.example.demo.pojo.Volunteering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ActivityService {
    private static final int SUCCESS = 1;
    private static final int FAIL = 0;
    @Autowired
    IVolunteeringDao dao;

    public List<Volunteering> getActivities(Volunteering v) {
        List<Volunteering> activity = dao.getAll();
        List<Volunteering> ans = new LinkedList<>();
        for (Volunteering x : activity) {
            if ((v.getStatus().equals(x.getStatus()) || v.getStatus() == 0)
                    && (v.getLocation().equals(x.getLocation()) || v.getLocation() == 0)
                    && (v.getType().equals(x.getType()) || v.getType() == 0)
                    && (v.getPNum().equals(x.getPNum()) || v.getPNum() == 0)) {
                ans.add(x);
            }
        }
        return ans;
    }

    public int addActivity(Volunteering v) {
        try {
            dao.addVol(v);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
            return FAIL;
        }
        return SUCCESS;
    }

    public Volunteering getOneActivity(int id) {
        Volunteering v = null;
        try {
            v = dao.getVolById(id).get(0);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
            return v;
        }
        return v;

    }

}
