package com.testplatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.testplatform.entity.TestResult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestResultMapper extends BaseMapper<TestResult> {
}