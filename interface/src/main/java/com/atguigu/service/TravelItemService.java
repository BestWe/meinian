package com.atguigu.service;

import com.atguigu.enity.PageResult;
import com.atguigu.pojo.TravelItem;

import java.util.List;

public interface TravelItemService {
    void addTravelItem(TravelItem travelItem);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void removeTravelItem(Integer id);

    TravelItem findByTravelItemId(Integer id);

    void updateTravelItem(TravelItem travelItem);

    List<TravelItem> findAll();
}
