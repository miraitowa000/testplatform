package com.testplatform.controller;

import com.testplatform.common.ResultVO;
import com.testplatform.dto.UserRegisterDTO;
import com.testplatform.dto.UserLoginDTO;
import com.testplatform.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户管理", description = "用户相关接口")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public ResultVO register(@Validated @RequestBody UserRegisterDTO registerDTO) {

        return userService.register(registerDTO);
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public ResultVO login(@Validated @RequestBody UserLoginDTO loginDTO) {

        return userService.login(loginDTO);

    }

    @Operation(summary = "用户退出登录")
    @GetMapping("/logout")
    public ResultVO logout(@RequestHeader("Authorization") String authHeader) {
        return userService.logout(authHeader);
    }
}