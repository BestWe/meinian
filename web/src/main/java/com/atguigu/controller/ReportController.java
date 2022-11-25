package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.enity.Result;
import com.atguigu.service.MemberService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/report")
public class ReportController {
    @DubboReference
    private MemberService memberService;

    @ResponseBody
    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        Calendar calendar = Calendar.getInstance();
        //根据当前时间，根据月份减去12个月，获得距今12个月的去年的时间
        calendar.add(Calendar.MONTH, -12);
        List<String> list = new ArrayList<>();
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            /*通过for循环，每次向日期增加一个月，并将日历转化为时间存储到集合中*/
            calendar.add(Calendar.MONTH, 1);
            /*改进，必须获取每个月的最后一天，否则查询报错*/
            int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, lastDay);
            list.add(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
            result.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
        }
        Map<String, Object> map = new HashMap<>();
        List<Integer> memberCount = memberService.findMemberCountByMonth(list);
        map.put("month", result);
        map.put("memberCount", memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);

    }
}
