package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.enity.PageResult;
import com.atguigu.enity.QueryPageBean;
import com.atguigu.enity.Result;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/travelItem")
public class TravelItemController {
    @DubboReference
    private TravelItemService travelItemService;

    @RequestMapping("/add")
    @ResponseBody
    @PreAuthorize("hasAuthority('TRAVELITEM_ADD')")
    public Result add(@RequestBody TravelItem travelItem) {
        try {
            travelItemService.addTravelItem(travelItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/findPage")
    @PreAuthorize("hasAuthority('TRAVELITEM_QUERY')")
    public PageResult findPage(@RequestBody @NonNull QueryPageBean queryPageBean) {
        PageResult page = travelItemService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return page;
    }

    @ResponseBody
    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('TRAVELITEM_DELETE')")
    public Result remove(Integer id) {
        try {
            travelItemService.removeTravelItem(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_TRAVELITEM_SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/findById")
    @PreAuthorize("hasAuthority('TRAVELITEM_QUERY')")
    public Result findById(Integer id) {
        TravelItem travelItem = travelItemService.findByTravelItemId(id);
        return new Result(true, MessageConstant.QUERY_TRAVELITEM_SUCCESS, travelItem);
    }

    @ResponseBody
    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('TRAVELITEM_EDIT')")
    public Result editTravelItem(@RequestBody TravelItem travelItem) {
        try {
            travelItemService.updateTravelItem(travelItem);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_TRAVELITEM_SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/findAll")
    @PreAuthorize("hasAuthority('TRAVELITEM_QUERY')")
    public Result findAll() {
        List<TravelItem> list = travelItemService.findAll();
        return new Result(true, MessageConstant.QUERY_TRAVELITEM_SUCCESS, list);
    }
}
