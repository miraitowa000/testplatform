package com.testplatform.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ApiTestDTO {
    /**
     * 请求方法（GET, POST, PUT, DELETE等）
     */
    private String method;

    /**
     * 请求URL
     */
    private String url;

    /**
     * 请求头
     */
    private Map<String, String> headers;

    /**
     * 查询参数
     */
    private Map<String, String> params;

    /**
     * 请求体
     * 使用Object类型以支持多种格式：
     * - JSON对象
     * - 表单数据
     * - 文本内容
     * 等
     */
    private Object body;
}