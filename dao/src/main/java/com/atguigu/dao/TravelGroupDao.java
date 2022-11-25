package com.atguigu.dao;

import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface TravelGroupDao {
    void addTravelGroup(TravelGroup travelGroup);

    void addTravelGroupRelationTravelItem(Map<String, Integer> map);

    void deleteTravelGroup(Integer id);

    void deleteTravelGroupRelationTravelItem(Integer id);

    void updateTravelGroup(TravelGroup travelGroup);

    TravelGroup findByTravelGroupId(Integer id);

    Page<TravelGroup> findPage(String queryString);

    List<Integer> findTravelItemIdByTravelGroupId(Integer id);

    List<TravelGroup> findAll();

    List<TravelGroup> findTravelGroupListById();
}
