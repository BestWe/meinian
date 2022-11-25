package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisConstant;
import com.atguigu.enity.Result;
import com.atguigu.service.OrderMobileService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisPool;

@Controller
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @DubboReference
    private OrderMobileService orderMobileService;

    @ResponseBody
    @RequestMapping("/send4Order")
    public Result sendMessage(@RequestParam("telephone") String phone) {
        String code = orderMobileService.sendMessage(phone);
        //设置生存时间
        jedisPool.getResource().setex(phone + RedisConstant.SENDTYPE_ORDER, 5 * 60, code);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
    @ResponseBody
    @RequestMapping("/send4Login")
    public Result sendMessageLogin(@RequestParam("telephone") String phone){
        String code = orderMobileService.sendMessage(phone);
        //设置生存时间
        jedisPool.getResource().setex(phone + RedisConstant.SENDTYPE_LOGIN, 5 * 60, code);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
