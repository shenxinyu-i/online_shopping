package com.shopcoupon.common.constant;

/**
 * 公共常量
 */
public final class CommonConstants {

    private CommonConstants() {
    }

    /** JWT请求头 */
    public static final String AUTH_HEADER = "Authorization";

    /** JWT前缀 */
    public static final String AUTH_PREFIX = "Bearer ";

    /** 用户ID请求头（网关透传） */
    public static final String USER_ID_HEADER = "X-User-Id";

    /** 用户角色请求头（网关透传） */
    public static final String USER_ROLE_HEADER = "X-User-Role";

    /** Seata事务组 */
    public static final String SEATA_TX_GROUP = "shopcoupon-tx-group";

    /** 角色：普通用户 */
    public static final String ROLE_USER = "USER";

    /** 角色：商家 */
    public static final String ROLE_MERCHANT = "MERCHANT";

    /** 角色：管理员 */
    public static final String ROLE_ADMIN = "ADMIN";
}
