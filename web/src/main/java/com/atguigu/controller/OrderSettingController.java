package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.enity.Result;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderSettingService;
import com.atguigu.utils.POIUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @DubboReference
    private OrderSettingService orderSettingService;

    @ResponseBody
    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        try {
            List<String[]> strings = POIUtils.readExcel(excelFile);
            List<OrderSetting> list = new ArrayList<>();
            for (String[] str : strings) {
                list.add(new OrderSetting(new Date(str[0]), Integer.parseInt(str[1])));
            }
            orderSettingService.add(list);
        } catch (Exception e) {
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date) {
        List<Map<String, Integer>> mapList = null;
        try {
            mapList = orderSettingService.findOrderSettingByDate(date);
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, mapList);
    }

    @ResponseBody
    @RequestMapping("/editNumberByDate")
    public Result editOrderSetting(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.editNumberByDate(orderSetting);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }
}
