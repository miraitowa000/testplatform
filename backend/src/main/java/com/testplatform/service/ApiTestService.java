package com.testplatform.service;

import com.testplatform.common.ResultVO;
import com.testplatform.dto.ApiTestDTO;

public interface ApiTestService {
    /**
     * 执行接口测试
     * @param apiTestDTO 接口测试参数
     * @return 测试结果
     */
    ResultVO executeTest(ApiTestDTO apiTestDTO);
}