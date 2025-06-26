package com.testplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testplatform.common.ResultVO;
import com.testplatform.dto.TestCaseDTO;
import com.testplatform.entity.TestCase;
import com.testplatform.mapper.TestCaseMapper;
import com.testplatform.service.TestCaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCaseServiceImpl extends ServiceImpl<TestCaseMapper, TestCase> implements TestCaseService {

    @Override
    public ResultVO createTestCase(TestCaseDTO testCaseDTO) {
        TestCase testCase = new TestCase();
        BeanUtils.copyProperties(testCaseDTO, testCase);
        if (save(testCase)) {
            return new ResultVO(200, "创建测试用例成功", testCase);
        }
        return new ResultVO(500, "创建测试用例失败", null);
    }

    @Override
    public ResultVO updateTestCase(Long id, TestCaseDTO testCaseDTO) {
        TestCase testCase = getById(id);
        if (testCase == null) {
            return new ResultVO(404, "测试用例不存在", null);
        }
        BeanUtils.copyProperties(testCaseDTO, testCase);
        if (updateById(testCase)) {
            return new ResultVO(200, "更新测试用例成功", testCase);
        }
        return new ResultVO(500, "更新测试用例失败", null);
    }

    @Override
    public ResultVO deleteTestCase(Long id) {
        if (removeById(id)) {
            return new ResultVO(200, "删除测试用例成功", null);
        }
        return new ResultVO(500, "删除测试用例失败", null);
    }

    @Override
    public ResultVO getTestCaseById(Long id) {
        TestCase testCase = getById(id);
        if (testCase != null) {
            return new ResultVO(200, "获取测试用例成功", testCase);
        }
        return new ResultVO(404, "测试用例不存在", null);
    }

    @Override
    public ResultVO listTestCases(Long projectId) {
        LambdaQueryWrapper<TestCase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TestCase::getProjectId, projectId);
        List<TestCase> testCases = list(queryWrapper);
        return new ResultVO(200, "获取测试用例列表成功", testCases);
    }

    @Override
    public ResultVO listTestCasesByStatus(Long projectId, Integer status) {
        LambdaQueryWrapper<TestCase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TestCase::getProjectId, projectId)
                .eq(TestCase::getStatus, status);
        List<TestCase> testCases = list(queryWrapper);
        return new ResultVO(200, "获取测试用例列表成功", testCases);
    }

    @Override
    public ResultVO listTestCasesByExecutor(Long executorId) {
        LambdaQueryWrapper<TestCase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TestCase::getExecutorId, executorId);
        List<TestCase> testCases = list(queryWrapper);
        return new ResultVO(200, "获取测试用例列表成功", testCases);
    }

    @Override
    public ResultVO assignTestCase(Long id, Long executorId) {
        TestCase testCase = getById(id);
        if (testCase == null) {
            return new ResultVO(404, "测试用例不存在", null);
        }
        testCase.setExecutorId(executorId);
        if (updateById(testCase)) {
            return new ResultVO(200, "分配测试用例成功", testCase);
        }
        return new ResultVO(500, "分配测试用例失败", null);
    }

    @Override
    public ResultVO updateTestCaseStatus(Long id, Integer status) {
        TestCase testCase = getById(id);
        if (testCase == null) {
            return new ResultVO(404, "测试用例不存在", null);
        }
        testCase.setStatus(status);
        if (updateById(testCase)) {
            return new ResultVO(200, "更新测试用例状态成功", testCase);
        }
        return new ResultVO(500, "更新测试用例状态失败", null);
    }
}