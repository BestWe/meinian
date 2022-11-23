package com.atguigu.dao;

import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    void addSetmeal(Setmeal setmeal);

    void addSetmealRelationTravelGroup(Map<String, Integer> map);

    void removeSetmeal(Integer id);

    void removeSetmealRelationTravelGroup(Integer setmealId);

    void updateSetmeal(Setmeal setmeal);

    Setmeal findById(Integer id);

    Page<Setmeal> findPage(String queryString);

    List<Integer> findBySetmealIdRelationTravelGroup(Integer id);
}
