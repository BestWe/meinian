package com.atguigu.dao;

import com.atguigu.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    Long findCountByOrderDate(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    void edit(OrderSetting orderSetting);

    List<OrderSetting> findOrderSetting(Map<String, String> map);

    void editNumberByOrderDate(OrderSetting orderSetting);
}
