package com.shopcoupon.auth.controller;

import com.shopcoupon.auth.dto.LoginRequest;
import com.shopcoupon.auth.dto.LoginResponse;
import com.shopcoupon.auth.dto.RegisterRequest;
import com.shopcoupon.auth.dto.UserInfoResponse;
import com.shopcoupon.auth.service.AuthService;
import com.shopcoupon.common.constant.CommonConstants;
import com.shopcoupon.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

  @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return Result.success("注册成功", null);
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return Result.success(authService.login(request));
    }

    @PostMapping("/refresh")
    public Result<LoginResponse> refresh(@RequestHeader("Refresh-Token") String refreshToken) {
        return Result.success(authService.refreshToken(refreshToken));
    }

    @GetMapping("/me")
    public Result<UserInfoResponse> getCurrentUser(
            @RequestHeader(CommonConstants.USER_ID_HEADER) Long userId) {
        return Result.success(authService.getCurrentUser(userId));
    }
}
