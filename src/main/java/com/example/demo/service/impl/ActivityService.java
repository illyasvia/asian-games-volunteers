package com.example.demo.service.impl;

import com.example.demo.common.ACTrie;
import com.example.demo.common.Result;
import com.example.demo.dao.IInforDao;
import com.example.demo.dao.IVolunteeringDao;
import com.example.demo.pojo.Volunteering;
import com.example.demo.service.IActivityService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 活动服务service
 * 负责管理活动、添加、删除、发布活动
 */
@Slf4j
@Service
public class ActivityService implements IActivityService {
    @Autowired
    private IVolunteeringDao iVolunteeringDao;
    @Autowired
    private IInforDao iInforDao;

    @Override
    public Result<?> getAll() {
        return Result.success(iVolunteeringDao.getAll());
    }

    /**
     * 按页查询
     *
     * @param currentPage 当前页数
     * @param rows        每页数量
     * @return 返回数据是 活动的List
     */
    @Override
    public Result<?> getByPage(Integer currentPage, Integer rows) {
        PageHelper.startPage(currentPage, rows);
        return Result.success(iVolunteeringDao.getAll());
    }

    @Override
    public Result<?> getVolunteering(Integer currentPage, Integer rows) {
        Result<?> result;
        if (rows <= 0) result = getAll();
        else result = getByPage(currentPage, rows);
        return result;
    }

    @Override
    public Result<?> getById(Integer vid) {
        return Result.success(iVolunteeringDao.getVolById(vid));
    }

    @Transactional
    @Override
    public Result<?> addVol(Volunteering v) {
        iVolunteeringDao.addVol(v);
        return Result.success();
    }

    @Transactional
    @Override
    public Result<?> updateVolById(Volunteering v) {
        iVolunteeringDao.updateVolById(v);
        return Result.success();
    }

    @Transactional
    @Override
    public Result<?> deleteVol(Integer vid) {
        Result<?> result;
        try {
            // 需要先删除报名该活动的人的信息
            iInforDao.deleteInforByVid(vid);
            iVolunteeringDao.deleteVol(vid);
            result = Result.success();
        } catch (Exception e) {
            result = Result.error(Result.UNKNOWN_ERROR, e.getMessage());
        }
        return result;
    }

    /**
     * 使用AC自动机进行模式串匹配搜索
     **/
    @Override
    public Result<?> searchByContent(String word) {
        ACTrie ac = new ACTrie();//ac自动机
        Map<Volunteering, Map<String, Float>> record = new HashMap<>();//记录多模式串匹配结果
        List<Volunteering> result_list = new LinkedList<>(); //返回搜索结果
        Map<String, Integer> count = new HashMap<>();

        /*获取用户搜索字段关键字信息*/
        val keyword = ACTrie.getSignalWord(word);
        for (val x : keyword) {
            ac.addKeyword(x);
        }

        //统计文章和标题关键字tf
        val volunteers = iVolunteeringDao.getAll();
        val SUM = volunteers.size();
        for (val x : volunteers) {
            record.put(x, ac.getTF(x, keyword, count));
        }

        //获取TF-IDF权重
        for (val x : volunteers) {
            float weight = 0f;
            for (val y : keyword) {
                weight += record.get(x).get(y) * Math.log((float) SUM / (count.getOrDefault(keyword, 0) + 1)) * (float) 100 / keyword.size();
            }
            if (weight > 5) result_list.add(x);
        }

        return Result.success(result_list);
    }

    @Override
    public Result<?> Filter(int region, int type, int status, int min, int max, Date start,Date end) {
        val array = iVolunteeringDao.getAll();
        List<Volunteering> ans = new LinkedList<>();
        for (val x : array) {
            if (region == 0 || region == x.getLocation() && (type == 0 || type == x.getType())&&(start.before(x.getStart()))&&(end.after(x.getEnd()))
                    && (status == 0 || status == x.getStatus()) && (x.getPNum() <= max && x.getPNum() >= min))
                ans.add(x);
        }
        return Result.success(ans);
    }
}
