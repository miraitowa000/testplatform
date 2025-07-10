package com.testplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // 设置连接超时时间（15秒）
        factory.setConnectTimeout(15000);
        // 设置读取超时时间（60秒）
        factory.setReadTimeout(60000);
        
        return new RestTemplate(factory);
    }
}