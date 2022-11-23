package com.atguigu.service;

import com.atguigu.enity.PageResult;
import com.atguigu.pojo.Setmeal;

import java.util.List;

public interface SetmealService {

    void add(Setmeal setmeal);

    void addRelationTravelGroup(Integer id, Integer[] travelGroupItems);

    void delete(Integer id);

    void deleteRelationTravelGroup(Integer id);

    void update(Setmeal setmeal);

    PageResult findPage(Integer currentPage, Integer PageSize, String queryString);

    Setmeal findBySetmealId(Integer id);

    List<Integer> findBySetmealIdRelationTravelGroup(Integer id);

}
