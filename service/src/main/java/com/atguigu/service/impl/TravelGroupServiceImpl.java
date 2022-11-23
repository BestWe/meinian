package com.atguigu.service.impl;

import com.atguigu.dao.TravelGroupDao;
import com.atguigu.enity.PageResult;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DubboService
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {
    @Autowired
    private TravelGroupDao travelGroupDao;

    @Override
    public void add(TravelGroup travelGroup, Integer[] travelItems) {
        travelGroupDao.addTravelGroup(travelGroup);
        this.setTravelGroupRelationTravelItem(travelGroup.getId(), travelItems);
    }

    @Override
    public void setTravelGroupRelationTravelItem(Integer travelGroupId, Integer[] travelItems) {
        if (travelItems != null && travelItems.length > 0) {
            Map<String, Integer> map = new HashMap<>();
            for (Integer travelItem : travelItems) {
                map.put("travelGroupId", travelGroupId);
                map.put("travelItemId", travelItem);
                travelGroupDao.addTravelGroupRelationTravelItem(map);
            }
        }
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<TravelGroup> page = travelGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public TravelGroup findByTravelGroup(Integer id) {
        return travelGroupDao.findByTravelGroupId(id);
    }

    @Override
    public void updateTravelGroup(TravelGroup travelGroup, Integer[] travelItems) {
        travelGroupDao.updateTravelGroup(travelGroup);
        //先删除原来的关系
        travelGroupDao.deleteTravelGroupRelationTravelItem(travelGroup.getId());
        this.setTravelGroupRelationTravelItem(travelGroup.getId(), travelItems);
    }

    @Override
    public List<Integer> findTravelItemIdByTravelGroupId(Integer id) {
        List<Integer> list = travelGroupDao.findTravelItemIdByTravelGroupId(id);
        return list;
    }

    @Override
    public void removeTravelGroup(Integer id) {
        //删除中间表对应关系
        travelGroupDao.deleteTravelGroupRelationTravelItem(id);
        //删除跟团游
        travelGroupDao.deleteTravelGroup(id);
    }
}
