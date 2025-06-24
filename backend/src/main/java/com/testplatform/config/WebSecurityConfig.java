package com.testplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // 放行Swagger相关路径
                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**", "/doc.html").permitAll()
                // 放行登录和注册接口
                .antMatchers("/api/user/login", "/api/user/register","/api/user/logout").permitAll()
                // 其他所有请求需要认证
                .anyRequest().authenticated()
                .and()
            .csrf().disable()  // 禁用CSRF
            .formLogin().disable()  // 禁用表单登录
            .httpBasic().disable(); // 禁用HTTP Basic认证

        return http.build();
    }
}