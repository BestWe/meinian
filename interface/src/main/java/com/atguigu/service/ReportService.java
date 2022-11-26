package com.atguigu.service;

import java.util.Map;

public interface ReportService {
    /**
     * 获取报表数据
     * @return 返回报表数据
     */
    Map<String, Object> getBusinessReportData();
}
