package com.atguigu.service;

import com.atguigu.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void add(List<OrderSetting> OrderSettings);

    List<Map<String, Integer>> findOrderSettingByDate(String orderDate);

    void editNumberByDate(OrderSetting orderSetting);
}
