package com.shopcoupon.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class AuthServiceImpl extends ServiceImpl<UserMapper, User> implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public void register(RegisterRequest request) {
        // 1. 校验用户名唯一性
        if (count(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, request.getUsername())) > 0) {
            throw new BusinessException(400, "用户名已被占用");
        }

        // 2. 校验手机号唯一性
        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            if (count(new LambdaQueryWrapper<User>()
                    .eq(User::getPhone, request.getPhone())) > 0) {
                throw new BusinessException(400, "手机号已被注册");
            }
        }

        // 3. 校验邮箱唯一性
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (count(new LambdaQueryWrapper<User>()
                    .eq(User::getEmail, request.getEmail())) > 0) {
                throw new BusinessException(400, "邮箱已被注册");
            }
        }

        // 4. 校验角色合法性（仅允许 USER / MERCHANT）
        String role = request.getRole();
        if (role == null || role.isBlank()) {
            role = CommonConstants.ROLE_USER;
        }
        if (!CommonConstants.ROLE_USER.equals(role) && !CommonConstants.ROLE_MERCHANT.equals(role)) {
            throw new BusinessException(400, "不支持的角色类型，仅允许 USER 或 MERCHANT");
        }

        // 5. BCrypt 加密后入库
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setRole(role);
        user.setStatus(1);

        save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = getOne(new LambdaQueryWrapper<User>()
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
        User user = getById(userId);
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
