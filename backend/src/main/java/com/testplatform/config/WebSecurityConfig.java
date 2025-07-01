package com.testplatform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

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
                .httpBasic().disable() // 禁用HTTP Basic认证
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 使用无状态会话
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // 添加JWT过滤器
                .anonymous(); // 允许匿名访问

        return http.build();
    }
}