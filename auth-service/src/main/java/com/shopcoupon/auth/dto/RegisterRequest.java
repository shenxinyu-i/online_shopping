package com.shopcoupon.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterRequest {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String phone;

    private String email;

    /** 注册角色，默认 USER */
    private String role;
}
