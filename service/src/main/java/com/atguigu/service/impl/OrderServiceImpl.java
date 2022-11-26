package com.atguigu.service.impl;

import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.enity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderService;
import com.atguigu.utils.Date2Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@DubboService(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Result order(Map map) throws Exception {
        String orderDate = (String) map.get("orderDate");
        Date date = Date2Utils.parseString2Date(orderDate);
        //先检查当前日期是否进行了预约
        OrderSetting orderSetting = orderSettingDao.findOneOrderSetting(orderDate);
        //获取为null则说明还没有进行开团设置
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //如果不为空，需要进行判断是否还有预约空位
        //最大预约数
        int number = orderSetting.getNumber();
        //实际预约人数
        int reservations = orderSetting.getReservations();
        if (reservations >= number) {
            //说明人数已满，不能进行预约
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        //获取手机号
        String telephone = (String) map.get("telephone");
        //判断是否注册会员
        Member member = memberDao.findByTelephone(telephone);
        Member newMember = new Member();
        if (member != null) {
            Integer id = member.getId();
            Integer setmealId = Integer.parseInt((String) map.get("setmealId"));
            Order order = new Order(id, date, null, null, setmealId);
            //根据预约信息查询是否已经预约
            List<Order> list = orderDao.findByCondition(order);
            //如果不为null，说明已经预约，不能重复预约
            if (list != null && list.size() > 0) {
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        } else {
            //如果不是会员，注册会员，向会员表中添加数据
//            Member newMember = new Member();
            newMember.setName((String) map.get("name"));
            newMember.setSex((String) map.get("sex"));
            newMember.setPhoneNumber((String) map.get("telephone"));
            newMember.setIdCard((String) map.get("idCard"));
            newMember.setRegTime(new Date());
            memberDao.add(newMember);//注册会员信息
        }
        //可以进行预约，更新预约设置表中的预约人数，使其值加1
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingDao.editReservationByOrderDate(orderSetting);
        //将预约信息保存到预约表中
        Order order = new Order();
        order.setMemberId(member != null ? member.getId() : newMember.getId());
        order.setOrderDate(date);
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        if (StringUtils.isBlank((String) map.get("orderType"))) {
            order.setOrderType(Order.ORDERTYPE_WEIXIN);
        } else {
            order.setOrderType((String) map.get("orderType"));
        }
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        orderDao.add(order);

        return new Result(true, MessageConstant.ORDER_SUCCESS, order);
    }

    @Override
    public Map<String, String> findById(Integer id) {
        return orderDao.findById4Detail(id);
    }
}
