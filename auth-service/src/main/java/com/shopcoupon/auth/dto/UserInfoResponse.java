package com.shopcoupon.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoResponse {

    private Long id;

    private String username;

    private String phone;

    private String email;

    private String role;
}
