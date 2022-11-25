package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.enity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;
import com.atguigu.service.TravelGroupService;
import com.atguigu.service.TravelItemService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/setmeal")
public class SetmealMobileController {
    @DubboReference
    private SetmealService setmealService;
    @DubboReference
    private TravelGroupService travelGroupService;
    @DubboReference
    private TravelItemService travelItemService;

    //获取所有套餐信息
    @RequestMapping("/getSetmeal")
    @ResponseBody
    public Result getSetmeal() {
        List<Setmeal> all = null;
        try {
            all = setmealService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, all);
    }

    @RequestMapping("/findById")
    @ResponseBody
    public Result findSetmealById(Integer id) {
        Setmeal setmeal = null;
        try {
            setmeal = setmealService.findBySetmealId(id);
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
    }
}
