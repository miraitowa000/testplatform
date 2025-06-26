package com.testplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.testplatform.common.ResultVO;
import com.testplatform.dto.TestResultDTO;
import com.testplatform.entity.TestResult;

public interface TestResultService extends IService<TestResult> {
    ResultVO createTestResult(TestResultDTO testResultDTO);
    ResultVO updateTestResult(Long id, TestResultDTO testResultDTO);
    ResultVO deleteTestResult(Long id);
    ResultVO getTestResultById(Long id);
    ResultVO listTestResults(Long caseId);
    ResultVO listTestResultsByStatus(Long caseId, Integer status);
    ResultVO listTestResultsByExecutor(Long executorId);
    ResultVO getTestResultStatistics(Long projectId);
}