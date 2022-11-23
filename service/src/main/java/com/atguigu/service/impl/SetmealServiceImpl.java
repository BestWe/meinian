package com.atguigu.service.impl;

import com.atguigu.dao.SetmealDao;
import com.atguigu.enity.PageResult;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DubboService
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;

    @Override
    public void add(Setmeal setmeal) {
        setmealDao.addSetmeal(setmeal);
    }

    @Override
    public void addRelationTravelGroup(Integer id, Integer[] travelGroupItems) {
        if (travelGroupItems != null && travelGroupItems.length > 0) {
            Map<String, Integer> map = new HashMap<>();
            for (Integer travelGroupId : travelGroupItems) {
                map.put("setmealId", id);
                map.put("travelGroupId", travelGroupId);
                setmealDao.addSetmealRelationTravelGroup(map);
            }
        }
    }

    @Override
    public void delete(Integer id) {
        setmealDao.removeSetmeal(id);
    }

    @Override
    public void deleteRelationTravelGroup(Integer id) {
        setmealDao.removeSetmealRelationTravelGroup(id);
    }

    @Override
    public void update(Setmeal setmeal) {
        setmealDao.updateSetmeal(setmeal);
    }


    @Override
    public PageResult findPage(Integer currentPage, Integer PageSize, String queryString) {
        PageHelper.startPage(currentPage, PageSize, queryString);
        Page<Setmeal> page = setmealDao.findPage(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Setmeal findBySetmealId(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public List<Integer> findBySetmealIdRelationTravelGroup(Integer id) {
        return setmealDao.findBySetmealIdRelationTravelGroup(id);
    }
}
