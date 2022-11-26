package com.atguigu.dao;

import com.atguigu.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    List<Order> findByCondition(Order order);

    void add(Order order);

    Order findById(Integer id);

    Map<String, String> findById4Detail(Integer id);

    Integer findOrderCountByDay(String today);

    Integer findOrderVisitsCountByDay(String today);

    Integer findOrderCountByTimes(@Param("begin") String begin, @Param("end") String end);

    Integer findOrderVisitsCountByTimes(@Param("begin") String begin, @Param("end") String end);

    List<Map<String, Object>> findHotSetmeal();
}
