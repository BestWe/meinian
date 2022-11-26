package com.atguigu.service;

import com.atguigu.enity.PageResult;
import com.atguigu.enity.QueryPageBean;
import com.atguigu.pojo.Address;

import java.util.List;

public interface AddressService {
    List<Address> findAll();

    void addAddress(Address address);

    PageResult findPage(QueryPageBean queryPageBean);

    void deleteById(Integer id);

}
