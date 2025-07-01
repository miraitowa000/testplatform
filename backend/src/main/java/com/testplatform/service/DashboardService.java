package com.testplatform.service;

import com.testplatform.common.ResultVO;

public interface DashboardService {
    /**
     * 获取仪表盘概览信息
     * 包括：
     * 1. 项目统计：总项目数、进行中项目数、已完成项目数
     * 2. 测试统计：接口总数、通过率、失败用例数
     * 3. 缺陷统计：总缺陷数、严重缺陷、高优先级缺陷
     */
    ResultVO getOverview();
}