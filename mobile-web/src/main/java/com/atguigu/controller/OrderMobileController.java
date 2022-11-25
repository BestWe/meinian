package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisConstant;
import com.atguigu.enity.Result;
import com.atguigu.service.OrderMobileService;
import com.atguigu.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@Api(tags = "订单管理")
@Controller
@RequestMapping("/order")
public class OrderMobileController {
    @DubboReference
    private OrderMobileService orderMobileService;
    @Autowired
    private JedisPool jedisPool;

    @DubboReference
    private OrderService orderService;
    @ApiOperation(value = "发送验证码")
    @ResponseBody
    @RequestMapping("/send4Order")
    public Result sendMessage(@RequestParam("telephone") String phone) {
        String code = orderMobileService.sendMessage(phone);
        //设置生存时间
        jedisPool.getResource().setex(phone + RedisConstant.SENDTYPE_ORDER, 5 * 60, code);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
    @ApiOperation(value = "新增预约")
    @ResponseBody
    @RequestMapping("/submit")
    public Result submitOrder(@RequestBody Map<String, String> map) {
        String phone = map.get("telephone");
        String code = map.get("validateCode");
        String s = jedisPool.getResource().get(phone + RedisConstant.SENDTYPE_ORDER);
        if (s == null || !s.equals(code)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Result result = null;
        try {
            result = orderService.order(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL);
        }
        if (result.isFlag()) {
            //todo 如果成功可以给用户发送预约成功的短信消息
        }
        return result;
    }
    @ApiOperation(value = "查询预约信息")
    @ResponseBody
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        Map<String, String> map = null;
        try {
            map = orderService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
    }
}
