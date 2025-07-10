package com.testplatform.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.http.HttpHeaders;

@Data
@Schema(description = "接口测试结果")
public class ApiTestResultVO {

    @Schema(description = "HTTP状态码", example = "200")
    private int statusCode;

    @Schema(description = "响应头")
    private HttpHeaders headers;

    @Schema(description = "响应体")
    private String body;

    @Schema(description = "响应时间（毫秒）", example = "100")
    private long responseTime;

    @Schema(description = "测试是否成功", example = "true")
    private boolean success;

    @Schema(description = "错误信息（如果测试失败）", example = "Connection timed out")
    private String errorMessage;
}