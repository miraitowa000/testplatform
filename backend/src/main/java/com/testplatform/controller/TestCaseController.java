package com.testplatform.controller;

import com.testplatform.common.ResultVO;
import com.testplatform.dto.TestCaseDTO;
import com.testplatform.service.TestCaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "测试用例管理", description = "测试用例相关接口")
@RestController
@RequestMapping("/api/testcase")
public class TestCaseController {

    @Autowired
    private TestCaseService testCaseService;

    @Operation(summary = "创建测试用例")
    @PostMapping
    public ResultVO createTestCase(@RequestBody TestCaseDTO testCaseDTO) {
        return testCaseService.createTestCase(testCaseDTO);
    }

    @Operation(summary = "更新测试用例")
    @PutMapping("/{id}")
    public ResultVO updateTestCase(
            @Parameter(description = "测试用例ID") @PathVariable Long id,
            @RequestBody TestCaseDTO testCaseDTO) {
        return testCaseService.updateTestCase(id, testCaseDTO);
    }

    @Operation(summary = "删除测试用例")
    @DeleteMapping("/{id}")
    public ResultVO deleteTestCase(
            @Parameter(description = "测试用例ID") @PathVariable Long id) {
        return testCaseService.deleteTestCase(id);
    }

    @Operation(summary = "获取测试用例详情")
    @GetMapping("/{id}")
    public ResultVO getTestCaseById(
            @Parameter(description = "测试用例ID") @PathVariable Long id) {
        return testCaseService.getTestCaseById(id);
    }

    @Operation(summary = "获取项目的测试用例列表")
    @GetMapping("/list/project/{projectId}")
    public ResultVO listTestCases(
            @Parameter(description = "项目ID") @PathVariable Long projectId) {
        return testCaseService.listTestCases(projectId);
    }

    @Operation(summary = "获取项目指定状态的测试用例列表")
    @GetMapping("/list/project/{projectId}/status/{status}")
    public ResultVO listTestCasesByStatus(
            @Parameter(description = "项目ID") @PathVariable Long projectId,
            @Parameter(description = "测试用例状态：0-未执行，1-通过，2-失败，3-阻塞") 
            @PathVariable Integer status) {
        return testCaseService.listTestCasesByStatus(projectId, status);
    }

    @Operation(summary = "获取执行人的测试用例列表")
    @GetMapping("/list/executor/{executorId}")
    public ResultVO listTestCasesByExecutor(
            @Parameter(description = "执行人ID") @PathVariable Long executorId) {
        return testCaseService.listTestCasesByExecutor(executorId);
    }

    @Operation(summary = "分配测试用例")
    @PutMapping("/{id}/assign/{executorId}")
    public ResultVO assignTestCase(
            @Parameter(description = "测试用例ID") @PathVariable Long id,
            @Parameter(description = "执行人ID") @PathVariable Long executorId) {
        return testCaseService.assignTestCase(id, executorId);
    }

    @Operation(summary = "更新测试用例状态")
    @PutMapping("/{id}/status/{status}")
    public ResultVO updateTestCaseStatus(
            @Parameter(description = "测试用例ID") @PathVariable Long id,
            @Parameter(description = "测试用例状态：0-未执行，1-通过，2-失败，3-阻塞") 
            @PathVariable Integer status) {
        return testCaseService.updateTestCaseStatus(id, status);
    }
}