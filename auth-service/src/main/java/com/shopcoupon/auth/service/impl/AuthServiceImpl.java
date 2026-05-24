package com.shopcoupon.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shopcoupon.auth.dto.LoginRequest;
import com.shopcoupon.auth.dto.LoginResponse;
import com.shopcoupon.auth.dto.RegisterRequest;
import com.shopcoupon.auth.dto.UserInfoResponse;
import com.shopcoupon.auth.entity.User;
import com.shopcoupon.auth.mapper.UserMapper;
import com.shopcoupon.auth.service.AuthService;
import com.shopcoupon.auth.util.JwtUtil;
import com.shopcoupon.common.constant.CommonConstants;
import com.shopcoupon.common.exception.BusinessException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public void register(RegisterRequest request) {
        // TODO: 校验用户名/手机号/邮箱唯一性
        // TODO: 校验角色合法性（仅允许 USER/MERCHANT）
        // TODO: BCrypt加密密码后入库
        throw new BusinessException("注册功能待完善");
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // TODO: 根据用户名查询用户
        // TODO: BCrypt校验密码
        // TODO: 校验用户状态是否启用
        // TODO: 签发 accessToken 和 refreshToken
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, request.getUsername()));
        if (user == null) {
            throw new BusinessException(401, "用户名或密码错误");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(401, "用户名或密码错误");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new BusinessException(403, "账号已被禁用");
        }
        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getUsername(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getUsername(), user.getRole());
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        // TODO: 解析 refreshToken 并校验有效期
        // TODO: 重新签发 accessToken 和 refreshToken
        Claims claims = jwtUtil.parseToken(refreshToken);
        Long userId = Long.parseLong(claims.getSubject());
        String username = claims.get("username", String.class);
        String role = claims.get("role", String.class);
        return LoginResponse.builder()
                .accessToken(jwtUtil.generateAccessToken(userId, username, role))
                .refreshToken(jwtUtil.generateRefreshToken(userId, username, role))
                .userId(userId)
                .username(username)
                .role(role)
                .build();
    }

    @Override
    public UserInfoResponse getCurrentUser(Long userId) {
        // TODO: 查询用户信息并脱敏返回
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        return UserInfoResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .phone(user.getPhone())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
