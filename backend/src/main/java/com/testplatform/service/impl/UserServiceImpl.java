package com.testplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testplatform.common.ResultVO;
import com.testplatform.dto.UserRegisterDTO;
import com.testplatform.dto.UserLoginDTO;
import com.testplatform.entity.User;
import com.testplatform.mapper.UserMapper;
import com.testplatform.service.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public ResultVO login(UserLoginDTO loginDTO) {
        // 登录时需要完整的用户信息
        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, loginDTO.getUsername()));
        if (user == null) {
            return new ResultVO(400, "用户不存在", null);
        }

        // 验证密码
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        JwtBuilder jwtBuilder = Jwts.builder();
        String token = jwtBuilder.setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setId(user.getId().toString())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();
        redisTemplate.opsForValue().set("token", token);
        // 创建包含 token 的返回数据
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);
        return new ResultVO(200, "登录成功", data);
    }


    @Override
    public ResultVO register(UserRegisterDTO registerDTO) {
        // 检查密码是否匹配
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            return new ResultVO(400, "两次密码不一致", null);
        }

        // 检查邮箱是否已存在
        ResultVO result = findByEmail(registerDTO.getEmail());
        if (result.getData() != null) {
            return new ResultVO(400, "邮箱已存在", null);
        }

        // 创建新用户
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        // 设置默认值
        user.setUsername(registerDTO.getEmail()); // 使用邮箱作为默认用户名
        user.setStatus(1); // 默认启用
        user.setAvatar(""); // 默认空头像

        // 保存用户
        save(user);

        return new ResultVO(200, "注册成功", null);
    }

    @Override
    public ResultVO findByUsername(String username) {
        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        return new ResultVO(200, "查询成功", user);
    }

    @Override
    public ResultVO findByEmail(String email) {
        // 只查询 id 和 email 字段，用于邮箱查重
        User user = getOne(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, email)
                .select(User::getId, User::getEmail));
        return new ResultVO(200, "查询成功", user);
    }

    @Override
    public ResultVO logout(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResultVO(400, "无效的token", null);
        }
        String token = authHeader.substring(7);
        // 假设token存储的key为"token"，实际项目中建议用用户唯一标识
        redisTemplate.delete("token");
        return new ResultVO(200, "退出登录成功", null);
    }
}