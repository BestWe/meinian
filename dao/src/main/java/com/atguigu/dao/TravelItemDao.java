package com.atguigu.dao;

import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;

import java.util.List;

public interface TravelItemDao {
    /**
     * 添加自由行信息
     *
     * @param item 自由行对象
     */
    void addTravelItem(TravelItem item);

    /**
     * 分页查询返回数据
     *
     * @param queryString
     * @return
     */
    Page<TravelItem> findPage(String queryString);

    /**
     * 根据id删除指定的自由行项目
     *
     * @param id
     */
    void removeTravelItem(Integer id);

    /**
     * 查询与中间表的数据的关联
     *
     * @param id
     * @return
     */
    Long findCountByTravelItemItemId(Integer id);

    /**
     * 根据id查询具体的信息
     *
     * @param id
     * @return
     */
    TravelItem findById(Integer id);

    /**
     * 更新信息
     */
    void updateTravelItem(TravelItem travelItem);

    /**
     * 查询所有自由行信息
     *
     * @return
     */
    List<TravelItem> findAll();

}
