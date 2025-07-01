package com.testplatform.controller;

import com.testplatform.common.ResultVO;
import com.testplatform.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "仪表盘管理", description = "仪表盘相关接口")
@RestController
@RequestMapping("/api/dashboard")
@PreAuthorize("isAuthenticated()")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Operation(summary = "获取仪表盘概览信息", description = "获取项目统计、测试统计、缺陷统计等信息")
    @GetMapping("/overview")
    public ResultVO getOverview() {
        return dashboardService.getOverview();
    }
}