package com.atguigu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Date2Utils {
    private static final Integer FEBRUARY = 28;
    //存储月份信息
    private static Map<Integer, Integer> monthDays = new HashMap<>();//初始化是为了不要报空指针

    static {
        monthDays.put(1, 31);
        monthDays.put(2, 28);//先按平年设置，后面会进行判断
        monthDays.put(3, 31);
        monthDays.put(4, 30);
        monthDays.put(5, 31);
        monthDays.put(6, 30);
        monthDays.put(7, 31);
        monthDays.put(8, 31);
        monthDays.put(9, 30);
        monthDays.put(10, 31);
        monthDays.put(11, 30);
        monthDays.put(12, 31);
    }

    /**
     * 返回当前月份的天数
     *
     * @param date
     * @return
     */
    public static Integer getMonthDay(String date) {
        //date 的日期格式必须是 ‘2020-09’这种
        String[] split = date.split("-");
        Integer month = Integer.valueOf(split[1]);
        //先判断是否是闰年
        if (isLeapYear(split[0]) && month == 2) {
            return FEBRUARY;
        }
        return monthDays.get(month);

    }

    /**
     * 根据输入的年份判断是否是闰年
     *
     * @param year 年份
     * @return 返回true代表是闰年，反之则不是
     */
    public static boolean isLeapYear(String year) {
        Integer y = Integer.valueOf(year);
        if (y % 4 == 0 & y % 100 != 0 || y % 400 == 0) {
            return true;
        }
        return false;
    }

    /**
     * 返回当前月份的日期范围
     *
     * @param date
     * @return
     */
    public static String[] monthOfDaysScope(String date) {
        Integer monthDay = getMonthDay(date);
        String[] list = new String[2];
        list[0] = date + "-01";
        list[1] = date + "-" + String.valueOf(monthDay);
        return list;
    }

    public static Date parseString2Date(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sdf.parse(date);
        return parse;
    }
}
