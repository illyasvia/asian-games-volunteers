package com.example.demo.service.impl;

import com.example.demo.common.ACTrie;
import com.example.demo.common.InformationException;
import com.example.demo.common.Result;
import com.example.demo.pojo.VolCondition;
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
        Result<?> result;
        try {
            if (v.getTitle() == null || "".equals(v.getTitle())) {
                throw new InformationException("活动标题不能为空");
            }
            iVolunteeringDao.addVol(v);
            result = Result.success();
        } catch (InformationException e) {
            result = Result.error(Result.INFORMATION_ERROR, e.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public Result<?> updateVolById(Volunteering v) {
        Result<?> result;
        try {
            if (v.getVid() == null) {
                throw new InformationException("未指明活动vid");
            }

            Integer num = iInforDao.getCountByVid(v.getVid());
            if (v.getPNum() != null && v.getPNum() < num) {
                throw new InformationException("修改后可报名人数小于已报名人数！");
            }
            iVolunteeringDao.updateVolById(v);
            result = Result.success();
        } catch (InformationException e) {
            result = Result.error(Result.INFORMATION_ERROR, e.getMessage());
        } catch (Exception e) {
            result = Result.error(Result.UNKNOWN_ERROR, "未知错误");
        }
        return result;
    }

    @Transactional
    @Override
    public Result<?> deleteVol(Integer vid) {
        Result<?> result;
        try {
            if (iVolunteeringDao.getVolById(vid).size() == 0) {
                throw new InformationException("没有该活动");
            }
            // 需要先删除报名该活动的人的信息
            iInforDao.deleteInforByVid(vid);
            iVolunteeringDao.deleteVol(vid);
            result = Result.success();
        } catch (InformationException e) {
            result = Result.error(Result.INFORMATION_ERROR, e.getMessage());
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
    public Result<?> Filter(VolCondition condition) {
        Result<?> result;
        try{
            if(condition.getCurrentPage() != null && condition.getRows() != null
                   && condition.getRows() != 0){
                PageHelper.startPage(condition.getCurrentPage(),condition.getRows());
            }
            result = Result.success(iVolunteeringDao.getByCondition(condition));
        } catch (Exception e){
            result = Result.error(Result.UNKNOWN_ERROR,"后台错误");
        }
        return result;
    }

    @Override
    public Result<?> updateImage(int vid, String url) {
        val vol=iVolunteeringDao.getVolById(vid).get(0);
        vol.setCover(url);
        iVolunteeringDao.updateVolById(vol);
        return  Result.success();
    }
}
