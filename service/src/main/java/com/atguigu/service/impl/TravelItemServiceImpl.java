package com.atguigu.service.impl;

import com.atguigu.dao.TravelItemDao;
import com.atguigu.enity.PageResult;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DubboService
@Transactional
public class TravelItemServiceImpl implements TravelItemService {
    @Autowired
    private TravelItemDao travelItemDao;

    @Override
    public void addTravelItem(TravelItem travelItem) {
        travelItemDao.addTravelItem(travelItem);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<TravelItem> page = travelItemDao.findPage(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void removeTravelItem(Integer id) {
        if (travelItemDao.findCountByTravelItemItemId(id) > 0) {
            throw new RuntimeException("不允许删除");
        }
        travelItemDao.removeTravelItem(id);
    }

    @Override
    public TravelItem findByTravelItemId(Integer id) {
        return travelItemDao.findById(id);
    }

    @Override
    public void updateTravelItem(TravelItem travelItem) {
        travelItemDao.updateTravelItem(travelItem);
    }

    @Override
    public List<TravelItem> findAll() {
        return travelItemDao.findAll();
    }
}
