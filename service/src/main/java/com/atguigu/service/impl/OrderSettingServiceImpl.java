package com.atguigu.service.impl;

import com.atguigu.dao.OrderSettingDao;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderSettingService;
import com.atguigu.utils.Date2Utils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DubboService
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> OrderSettings) {
        for (OrderSetting orderSetting : OrderSettings) {
            Long count = orderSettingDao.findCountByOrderDate(orderSetting);
            if (count > 0) {
                orderSettingDao.edit(orderSetting);
            }
            orderSettingDao.add(orderSetting);
        }
    }

    @Override
    public List<Map<String, Integer>> findOrderSettingByDate(String orderDate) {
        Map<String, String> paramOrderDate = new HashMap<>();
        String[] strings = Date2Utils.monthOfDaysScope(orderDate);
        paramOrderDate.put("dateBegin", strings[0]);
        paramOrderDate.put("dateEnd", strings[1]);
        List<OrderSetting> orderSetting = orderSettingDao.findOrderSetting(paramOrderDate);
        List<Map<String, Integer>> list = new ArrayList<>();
        for (OrderSetting setting : orderSetting) {
            Map<String, Integer> map = new HashMap<>();
            map.put("date", setting.getOrderDate().getDate());
            map.put("number", setting.getNumber());
            map.put("reservations", setting.getReservations());
            list.add(map);
        }
        return list;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        Long count = orderSettingDao.findCountByOrderDate(orderSetting);
        if (count > 0) {
            orderSettingDao.editNumberByOrderDate(orderSetting);
        } else {
            orderSettingDao.add(orderSetting);
        }
    }
}
