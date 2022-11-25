package com.atguigu.service;

import com.atguigu.enity.Result;
import com.atguigu.pojo.Order;

import java.util.Map;

public interface OrderService {
    Result order(Map map) throws Exception;

    Map<String,String> findById(Integer id);
}
