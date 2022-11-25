package com.atguigu.service.impl;

import com.atguigu.service.OrderMobileService;
import com.atguigu.utils.SMS2Utils;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class OrderMobileServiceImpl implements OrderMobileService {
    @Override
    public String sendMessage(String phone) {
        return SMS2Utils.sendMessages(phone);
    }
}
