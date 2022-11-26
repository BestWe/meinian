package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.enity.Result;
import com.atguigu.service.MemberService;
import com.atguigu.service.ReportService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/report")
public class ReportController {
    @DubboReference
    private MemberService memberService;

    @DubboReference
    private ReportService reportService;

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

    @ResponseBody
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        List<Map<String, Object>> list = memberService.getSetmealCount();
        Map<String, Object> map = new HashMap<>();
        map.put("setmealCount", list);
        List<String> setmealNames = new ArrayList<>();
        for (Map<String, Object> m : list) {
            String name = (String) m.get("name");
            setmealNames.add(name);
        }
        map.put("setmealNames", setmealNames);
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, map);
    }

    @ResponseBody
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        Map<String, Object> map = null;
        try {
            map = reportService.getBusinessReportData();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
        return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
    }

    /**
     * 导出Excel报表
     *
     * @return
     */
//    @ResponseBody
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> businessReportData = reportService.getBusinessReportData();
            String reportDate = (String) businessReportData.get("reportDate");
            Integer todayNewMember = (Integer) businessReportData.get("todayNewMember");
            Integer totalMember = (Integer) businessReportData.get("totalMember");
            Integer thisWeekNewMember = (Integer) businessReportData.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) businessReportData.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) businessReportData.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) businessReportData.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) businessReportData.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) businessReportData.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) businessReportData.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) businessReportData.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) businessReportData.get("hotSetmeal");
            /*获取模板文件路径*/
            String templateRealPath = request.getSession().getServletContext().getRealPath("template") + File.separator +
                    "report_template.xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(templateRealPath)));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);
            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日出游数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周出游数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月出游数

            int rowNum = 12;
            for (Map map : hotSetmeal) {//热门套餐
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                String proportion = (String) map.get("proportion");
                row = sheet.getRow(rowNum++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion);//占比
            }
            //通过输出流进行文件下载
            ServletOutputStream out = response.getOutputStream();
            // 下载的数据类型（excel类型）
            response.setContentType("application/vnd.ms-excel");
            // 设置下载形式(通过附件的形式下载)
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(out);

            out.flush();
            out.close();
            workbook.close();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL, null);

    }
}
