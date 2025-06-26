package com.testplatform.controller;

import com.testplatform.common.ResultVO;
import com.testplatform.dto.TestResultDTO;
import com.testplatform.service.TestResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "测试结果管理", description = "测试结果相关接口")
@RestController
@RequestMapping("/api/testresult")
public class TestResultController {

    @Autowired
    private TestResultService testResultService;

    @Operation(summary = "创建测试结果")
    @PostMapping
    public ResultVO createTestResult(@RequestBody TestResultDTO testResultDTO) {
        return testResultService.createTestResult(testResultDTO);
    }

    @Operation(summary = "更新测试结果")
    @PutMapping("/{id}")
    public ResultVO updateTestResult(
            @Parameter(description = "测试结果ID") @PathVariable Long id,
            @RequestBody TestResultDTO testResultDTO) {
        return testResultService.updateTestResult(id, testResultDTO);
    }

    @Operation(summary = "删除测试结果")
    @DeleteMapping("/{id}")
    public ResultVO deleteTestResult(
            @Parameter(description = "测试结果ID") @PathVariable Long id) {
        return testResultService.deleteTestResult(id);
    }

    @Operation(summary = "获取测试结果详情")
    @GetMapping("/{id}")
    public ResultVO getTestResultById(
            @Parameter(description = "测试结果ID") @PathVariable Long id) {
        return testResultService.getTestResultById(id);
    }

    @Operation(summary = "获取测试用例的测试结果列表")
    @GetMapping("/list/case/{caseId}")
    public ResultVO listTestResults(
            @Parameter(description = "测试用例ID") @PathVariable Long caseId) {
        return testResultService.listTestResults(caseId);
    }

    @Operation(summary = "获取测试用例指定状态的测试结果列表")
    @GetMapping("/list/case/{caseId}/status/{status}")
    public ResultVO listTestResultsByStatus(
            @Parameter(description = "测试用例ID") @PathVariable Long caseId,
            @Parameter(description = "测试结果状态：1-通过，2-失败，3-阻塞") 
            @PathVariable Integer status) {
        return testResultService.listTestResultsByStatus(caseId, status);
    }

    @Operation(summary = "获取执行人的测试结果列表")
    @GetMapping("/list/executor/{executorId}")
    public ResultVO listTestResultsByExecutor(
            @Parameter(description = "执行人ID") @PathVariable Long executorId) {
        return testResultService.listTestResultsByExecutor(executorId);
    }

    @Operation(summary = "获取项目的测试结果统计")
    @GetMapping("/statistics/project/{projectId}")
    public ResultVO getTestResultStatistics(
            @Parameter(description = "项目ID") @PathVariable Long projectId) {
        return testResultService.getTestResultStatistics(projectId);
    }
}