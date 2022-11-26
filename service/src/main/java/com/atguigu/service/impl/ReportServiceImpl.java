package com.atguigu.service.impl;

import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.service.ReportService;
import com.atguigu.utils.DateUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DubboService
public class ReportServiceImpl implements ReportService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Map<String, Object> getBusinessReportData() {
        /**
         * 获得运营统计数据
         * Map数据格式：
         *      reportDate（当前时间）--String
         *      todayNewMember（今日新增会员数） -> number
         *      totalMember（总会员数） -> number
         *      thisWeekNewMember（本周新增会员数） -> number
         *      thisMonthNewMember（本月新增会员数） -> number
         *      todayOrderNumber（今日预约数） -> number
         *      todayVisitsNumber（今日出游数） -> number
         *      thisWeekOrderNumber（本周预约数） -> number
         *      thisWeekVisitsNumber（本周出游数） -> number
         *      thisMonthOrderNumber（本月预约数） -> number
         *      thisMonthVisitsNumber（本月出游数） -> number
         *      hotSetmeal（热门套餐（取前4）） -> List<Setmeal>
         */
        Map<String, Object> map = null;
        try {
            //今天
            String today = DateUtils.parseDate2String(DateUtils.getToday());
            //本周一
            String weekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
            //本周日
            String weekSunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
            //本月一号
            String monthFirst = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
            //本月最后一天
            String monthLast = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());
            /*今日新增会员数*/
            Integer todayNewMember = memberDao.findMemberCountByDay(today);
            /*总会员数*/
            Integer totalMember = memberDao.findMemberCount();
            /*本周新增会员数*/
            Integer thisWeekNewMember = memberDao.findMemberCountByTimes(weekMonday, weekSunday);
            /*本月新增会员数*/
            Integer thisMonthNewMember = memberDao.findMemberCountByTimes(monthFirst, monthLast);
            /*今日预约数*/
            Integer todayOrderNumber = orderDao.findOrderCountByDay(today);
            /*今日出游数*/
            Integer todayVisitsNumber = orderDao.findOrderVisitsCountByDay(today);
            /*本周预约数*/
            Integer thisWeekOrderNumber = orderDao.findOrderCountByTimes(weekMonday, weekSunday);
            /*本周出游数*/
            Integer thisWeekVisitsNumber = orderDao.findOrderVisitsCountByTimes(weekMonday, weekSunday);
            /*本月预约数*/
            Integer thisMonthOrderNumber = orderDao.findOrderCountByTimes(monthFirst, monthLast);
            /*本月出游数*/
            Integer thisMonthVisitsNumber = orderDao.findOrderVisitsCountByTimes(monthFirst, monthLast);
            /*热门套餐（取前4）*/
            List<Map<String, Object>> hotSetmeal = orderDao.findHotSetmeal();
            map = new HashMap<>();
            map.put("reportDate", today);
            map.put("todayNewMember", todayNewMember);
            map.put("totalMember", totalMember);
            map.put("thisWeekNewMember", thisWeekNewMember);
            map.put("thisMonthNewMember", thisMonthNewMember);
            map.put("todayOrderNumber", todayOrderNumber);
            map.put("todayVisitsNumber", todayVisitsNumber);
            map.put("thisWeekOrderNumber", thisWeekOrderNumber);
            map.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
            map.put("thisMonthOrderNumber", thisMonthOrderNumber);
            map.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
            map.put("hotSetmeal", hotSetmeal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
