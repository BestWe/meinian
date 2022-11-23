package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.enity.PageResult;
import com.atguigu.enity.QueryPageBean;
import com.atguigu.enity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/travelgroup")
public class TravelGroupController {
    @DubboReference
    private TravelGroupService travelGroupService;

    @ResponseBody
    @RequestMapping("/add")
    public Result addTravelGroup(@RequestBody TravelGroup travelGroup, Integer[] travelItemIds) {
        try {
            travelGroupService.add(travelGroup, travelItemIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_TRAVELGROUP_FAIL);
        }
        return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult page = travelGroupService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return page;
    }

    @ResponseBody
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        TravelGroup byTravelGroup = travelGroupService.findByTravelGroup(id);
        return new Result(true, MessageConstant.QUERY_TRAVELGROUP_SUCCESS, byTravelGroup);
    }

    @ResponseBody
    @RequestMapping("/findTravelItemIdByTravelgroupId")
    public Result findTravelItemsIdsById(Integer id) {
        List<Integer> list = travelGroupService.findTravelItemIdByTravelGroupId(id);
        return new Result(true, MessageConstant.QUERY_TRAVELITEM_SUCCESS, list);
    }

    @ResponseBody
    @RequestMapping("/edit")
    public Result updateTravelGroup(@RequestBody TravelGroup travelGroup, Integer[] travelItemIds) {
        try {
            travelGroupService.updateTravelGroup(travelGroup, travelItemIds);
        } catch (Exception e) {
            return new Result(true, MessageConstant.EDIT_TRAVELGROUP_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Result removeTravelGroup(Integer id) {
        try {
            travelGroupService.removeTravelGroup(id);
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_TRAVELGROUP_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/findAll")
    public Result findAll() {
        List<TravelGroup> list = travelGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_TRAVELGROUP_SUCCESS, list);
    }
}
