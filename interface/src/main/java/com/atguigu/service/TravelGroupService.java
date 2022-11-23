package com.atguigu.service;

import com.atguigu.enity.PageResult;
import com.atguigu.pojo.TravelGroup;

import java.util.List;

public interface TravelGroupService {
    void add(TravelGroup travelGroup, Integer[] travelItems);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    TravelGroup findByTravelGroup(Integer id);

    void updateTravelGroup(TravelGroup travelGroup, Integer[] travelItems);

    void setTravelGroupRelationTravelItem(Integer travelGroupId, Integer[] travelItems);

    List<Integer> findTravelItemIdByTravelGroupId(Integer id);

    void removeTravelGroup(Integer id);

    List<TravelGroup> findAll();
}
