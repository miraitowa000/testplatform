package com.testplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.testplatform.common.ResultVO;
import com.testplatform.entity.Project;
import com.testplatform.entity.TestCase;
import com.testplatform.entity.TestResult;
import com.testplatform.service.DashboardService;
import com.testplatform.service.ProjectService;
import com.testplatform.service.TestCaseService;
import com.testplatform.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private TestResultService testResultService;

     @Override
    public ResultVO getOverview() {
        Map<String, Object> overview = new HashMap<>();
        
        // 1. 项目统计
        Map<String, Object> projectStats = getProjectStatistics();
        overview.put("projectStatistics", projectStats);
        
        // 2. 测试统计
        Map<String, Object> testStats = getTestStatistics();
        overview.put("testStatistics", testStats);
        
        // 3. 缺陷统计
        Map<String, Object> defectStats = getDefectStatistics();
        overview.put("defectStatistics", defectStats);
        
        return new ResultVO(200, "数据获取成功", overview);
    }
    
    private Map<String, Object> getProjectStatistics() {
        // 获取当前时间
        Date now = new Date();

        // 查询所有项目
        List<Project> projects = projectService.list();

        // 统计项目状态
        int totalProjects = projects.size();
        int inProgressProjects = 0;
        int completedProjects = 0;

        for (Project project : projects) {
            if (project.getStartTime().before(now) && project.getEndTime().after(now)) {
                inProgressProjects++;
            } else if (project.getEndTime().before(now)) {
                completedProjects++;
            }
        }

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("total", totalProjects);
        statistics.put("ongoing", inProgressProjects);
        statistics.put("completed", completedProjects);

        return statistics;
    }

    private Map<String, Object> getTestStatistics() {
        // 查询所有测试用例
        List<TestCase> testCases = testCaseService.list();
        int totalTestCases = testCases.size();

        // 查询所有测试结果
        List<TestResult> testResults = testResultService.list();
        
        // 统计测试结果
        int passedTests = 0;
        int failedTests = 0;
        
        for (TestResult result : testResults) {
            if (result.getStatus() == 1) { // 1-通过
                passedTests++;
            } else if (result.getStatus() == 2) { // 2-失败
                failedTests++;
            }
        }

        // 计算通过率
        double passRate = totalTestCases > 0 ? (double) passedTests / totalTestCases * 100 : 0;

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("total", totalTestCases);
        statistics.put("passRate", String.format("%.2f%%", passRate));
        statistics.put("failCount", failedTests);

        return statistics;
    }

    private Map<String, Object> getDefectStatistics() {
        // 查询所有测试结果中的缺陷
        List<TestResult> testResults = testResultService.list(
            new LambdaQueryWrapper<TestResult>()
                .eq(TestResult::getStatus, 2) // 2-失败
        );

        int totalDefects = testResults.size();
        int criticalDefects = 0;
        int highPriorityDefects = 0;

        for (TestResult result : testResults) {
            TestCase testCase = testCaseService.getById(result.getCaseId());
            if (testCase != null) {
                if (testCase.getPriority() == 1) { // 1-最高优先级
                    criticalDefects++;
                } else if (testCase.getPriority() == 2) { // 2-高优先级
                    highPriorityDefects++;
                }
            }
        }

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("total", totalDefects);
        statistics.put("critical", criticalDefects);
        statistics.put("high", highPriorityDefects);

        return statistics;
    }
}