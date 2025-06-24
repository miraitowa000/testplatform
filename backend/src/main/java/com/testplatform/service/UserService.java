package com.testplatform.service;

import com.testplatform.common.ResultVO;
import com.testplatform.dto.UserLoginDTO;
import com.testplatform.dto.UserRegisterDTO;
import com.testplatform.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    ResultVO login(UserLoginDTO loginDTO);

    /**
     * 用户注册
     * @param registerDTO 注册信息
     * @return 注册成功的用户信息
     */
    public ResultVO register(UserRegisterDTO registerDTO);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    ResultVO findByUsername(String username);

    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return 用户信息
     */
    ResultVO findByEmail(String email);

    /**
     * 用户退出登录
     * @param authHeader 请求头中的Authorization
     * @return 操作结果
     */
    ResultVO logout(String authHeader);
}