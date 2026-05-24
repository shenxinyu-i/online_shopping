package com.shopcoupon.shop.controller;

import com.shopcoupon.common.constant.CommonConstants;
import com.shopcoupon.common.result.Result;
import com.shopcoupon.shop.dto.ShopRequest;
import com.shopcoupon.shop.entity.Shop;
import com.shopcoupon.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/shop/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PostMapping
    public Result<Shop> createShop(
            @RequestHeader(CommonConstants.USER_ID_HEADER) Long userId,
            @Valid @RequestBody ShopRequest request) {
        return Result.success(shopService.createShop(userId, request));
    }

    @PutMapping("/{shopId}")
    public Result<Shop> updateShop(
            @PathVariable Long shopId,
            @RequestHeader(CommonConstants.USER_ID_HEADER) Long userId,
            @Valid @RequestBody ShopRequest request) {
        return Result.success(shopService.updateShop(shopId, userId, request));
    }

    @GetMapping("/{shopId}")
    public Result<Shop> getShop(@PathVariable Long shopId) {
        return Result.success(shopService.getShopById(shopId));
    }

    @GetMapping("/my")
    public Result<List<Shop>> listMyShops(
            @RequestHeader(CommonConstants.USER_ID_HEADER) Long userId) {
        return Result.success(shopService.listShopsByOwner(userId));
    }

    @PutMapping("/{shopId}/status")
    public Result<Void> updateStatus(
            @PathVariable Long shopId,
            @RequestHeader(CommonConstants.USER_ID_HEADER) Long userId,
            @RequestParam Integer status) {
        shopService.updateShopStatus(shopId, userId, status);
        return Result.success(null);
    }
}
