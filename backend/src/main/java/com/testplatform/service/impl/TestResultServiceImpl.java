package com.testplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testplatform.common.ResultVO;
import com.testplatform.dto.TestResultDTO;
import com.testplatform.entity.TestCase;
import com.testplatform.entity.TestResult;
import com.testplatform.mapper.TestResultMapper;
import com.testplatform.service.TestCaseService;
import com.testplatform.service.TestResultService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestResultServiceImpl extends ServiceImpl<TestResultMapper, TestResult> implements TestResultService {

    @Autowired
    private TestCaseService testCaseService;

    @Override
    @Transactional
    public ResultVO createTestResult(TestResultDTO testResultDTO) {
        // 检查测试用例是否存在
        TestCase testCase = testCaseService.getById(testResultDTO.getCaseId());
        if (testCase == null) {
            return new ResultVO(404, "测试用例不存在", null);
        }

        TestResult testResult = new TestResult();
        BeanUtils.copyProperties(testResultDTO, testResult);
        testResult.setExecuteTime(new Date());

        if (save(testResult)) {
            // 更新测试用例状态
            testCase.setStatus(testResultDTO.getStatus());
            testCaseService.updateById(testCase);
            return new ResultVO(200, "创建测试结果成功", testResult);
        }
        return new ResultVO(500, "创建测试结果失败", null);
    }

    @Override
    public ResultVO updateTestResult(Long id, TestResultDTO testResultDTO) {
        TestResult testResult = getById(id);
        if (testResult == null) {
            return new ResultVO(404, "测试结果不存在", null);
        }
        BeanUtils.copyProperties(testResultDTO, testResult);
        if (updateById(testResult)) {
            return new ResultVO(200, "更新测试结果成功", testResult);
        }
        return new ResultVO(500, "更新测试结果失败", null);
    }

    @Override
    public ResultVO deleteTestResult(Long id) {
        if (removeById(id)) {
            return new ResultVO(200, "删除测试结果成功", null);
        }
        return new ResultVO(500, "删除测试结果失败", null);
    }

    @Override
    public ResultVO getTestResultById(Long id) {
        TestResult testResult = getById(id);
        if (testResult != null) {
            return new ResultVO(200, "获取测试结果成功", testResult);
        }
        return new ResultVO(404, "测试结果不存在", null);
    }

    @Override
    public ResultVO listTestResults(Long caseId) {
        LambdaQueryWrapper<TestResult> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TestResult::getCaseId, caseId)
                .orderByDesc(TestResult::getExecuteTime);
        List<TestResult> testResults = list(queryWrapper);
        return new ResultVO(200, "获取测试结果列表成功", testResults);
    }

    @Override
    public ResultVO listTestResultsByStatus(Long caseId, Integer status) {
        LambdaQueryWrapper<TestResult> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TestResult::getCaseId, caseId)
                .eq(TestResult::getStatus, status)
                .orderByDesc(TestResult::getExecuteTime);
        List<TestResult> testResults = list(queryWrapper);
        return new ResultVO(200, "获取测试结果列表成功", testResults);
    }

    @Override
    public ResultVO listTestResultsByExecutor(Long executorId) {
        LambdaQueryWrapper<TestResult> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TestResult::getExecutorId, executorId)
                .orderByDesc(TestResult::getExecuteTime);
        List<TestResult> testResults = list(queryWrapper);
        return new ResultVO(200, "获取测试结果列表成功", testResults);
    }

    @Override
    public ResultVO getTestResultStatistics(Long projectId) {
        // 获取项目下所有测试用例
        LambdaQueryWrapper<TestCase> caseWrapper = new LambdaQueryWrapper<>();
        caseWrapper.eq(TestCase::getProjectId, projectId);
        List<TestCase> testCases = testCaseService.list(caseWrapper);

        // 统计各状态数量
        int totalCount = testCases.size();
        int passCount = 0;
        int failCount = 0;
        int blockCount = 0;
        int notExecutedCount = 0;

        for (TestCase testCase : testCases) {
            switch (testCase.getStatus()) {
                case 0:
                    notExecutedCount++;
                    break;
                case 1:
                    passCount++;
                    break;
                case 2:
                    failCount++;
                    break;
                case 3:
                    blockCount++;
                    break;
            }
        }

        // 计算通过率
        double passRate = totalCount > 0 ? (double) passCount / totalCount * 100 : 0;

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalCount", totalCount);
        statistics.put("passCount", passCount);
        statistics.put("failCount", failCount);
        statistics.put("blockCount", blockCount);
        statistics.put("notExecutedCount", notExecutedCount);
        statistics.put("passRate", String.format("%.2f%%", passRate));

        return new ResultVO(200, "获取测试结果统计成功", statistics);
    }
}