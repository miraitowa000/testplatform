package com.testplatform.service.impl;

import com.testplatform.common.ResultVO;
import com.testplatform.dto.ApiTestDTO;
import com.testplatform.service.ApiTestService;
import com.testplatform.vo.ApiTestResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ApiTestServiceImpl implements ApiTestService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResultVO executeTest(ApiTestDTO apiTestDTO) {
        long startTime = System.currentTimeMillis();
        ApiTestResultVO resultVO = new ApiTestResultVO();

        try {
            // 构建请求头
            HttpHeaders headers = new HttpHeaders();
            if (apiTestDTO.getHeaders() != null) {
                apiTestDTO.getHeaders().forEach(headers::add);
            }

            // 构建URL（包含查询参数）
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiTestDTO.getUrl());
            if (apiTestDTO.getParams() != null) {
                apiTestDTO.getParams().forEach(builder::queryParam);
            }

            // 设置请求方法和请求体
            HttpMethod method = HttpMethod.valueOf(apiTestDTO.getMethod().toUpperCase());
            Object requestBody = null;
            
            // 根据Content-Type处理请求体
            String contentType = headers.getFirst(HttpHeaders.CONTENT_TYPE);
            if (apiTestDTO.getBody() != null) {
                // 默认使用JSON格式，除非明确指定其他格式
                if (contentType != null && !contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
                    // 对于明确指定的非JSON类型，使用字符串格式
                    requestBody = apiTestDTO.getBody().toString();
                } else {
                    // 默认使用JSON格式或者Content-Type包含JSON
                    requestBody = apiTestDTO.getBody();
                    // 如果没有设置Content-Type，自动设置为JSON
                    if (contentType == null) {
                        headers.setContentType(MediaType.APPLICATION_JSON);
                    }
                }
            }


            HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, headers);

            // 发送请求并获取响应
            ResponseEntity<String> response = restTemplate.exchange(
                builder.build().toUri(),
                method,
                requestEntity,
                String.class
            );

            // 设置响应结果
            resultVO.setStatusCode(response.getStatusCodeValue());
            resultVO.setHeaders(response.getHeaders());
            resultVO.setBody(response.getBody());
            resultVO.setSuccess(true);

        } catch (Exception e) {
            resultVO.setStatusCode(500);
            resultVO.setSuccess(false);
            resultVO.setErrorMessage(e.getMessage());
        } finally {
            // 计算响应时间
            long endTime = System.currentTimeMillis();
            resultVO.setResponseTime(endTime - startTime);
        }

        return new ResultVO(200, "接口测试执行完成", resultVO);
    }
}