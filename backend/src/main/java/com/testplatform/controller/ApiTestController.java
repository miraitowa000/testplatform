package com.testplatform.controller;

import com.testplatform.common.ResultVO;
import com.testplatform.dto.ApiTestDTO;
import com.testplatform.service.ApiTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "接口测试", description = "接口测试相关接口")
@RestController
@RequestMapping("/api/test")
public class ApiTestController {

    @Autowired
    private ApiTestService apiTestService;

    @Operation(summary = "执行接口测试")
    @PostMapping("/execute")
    public ResultVO executeTest(@Validated @RequestBody ApiTestDTO apiTestDTO) {
        return apiTestService.executeTest(apiTestDTO);
    }
}