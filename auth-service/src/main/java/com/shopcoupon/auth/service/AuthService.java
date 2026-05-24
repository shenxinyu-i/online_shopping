package com.shopcoupon.auth.service;

import com.shopcoupon.auth.dto.LoginRequest;
import com.shopcoupon.auth.dto.LoginResponse;
import com.shopcoupon.auth.dto.RegisterRequest;
import com.shopcoupon.auth.dto.UserInfoResponse;

public interface AuthService {

    /**
     * 用户注册
     */
    void register(RegisterRequest request);

    /**
     * 用户登录，签发JWT
     */
    LoginResponse login(LoginRequest request);

    /**
     * 刷新Token
     */
    LoginResponse refreshToken(String refreshToken);

    /**
     * 获取当前用户信息
     */
    UserInfoResponse getCurrentUser(Long userId);
}
