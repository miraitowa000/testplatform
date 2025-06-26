package com.testplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.testplatform.common.ResultVO;
import com.testplatform.dto.TestCaseDTO;
import com.testplatform.entity.TestCase;

public interface TestCaseService extends IService<TestCase> {
    ResultVO createTestCase(TestCaseDTO testCaseDTO);
    ResultVO updateTestCase(Long id, TestCaseDTO testCaseDTO);
    ResultVO deleteTestCase(Long id);
    ResultVO getTestCaseById(Long id);
    ResultVO listTestCases(Long projectId);
    ResultVO listTestCasesByStatus(Long projectId, Integer status);
    ResultVO listTestCasesByExecutor(Long executorId);
    ResultVO assignTestCase(Long id, Long executorId);
    ResultVO updateTestCaseStatus(Long id, Integer status);
}