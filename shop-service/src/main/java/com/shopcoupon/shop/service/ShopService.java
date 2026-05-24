package com.shopcoupon.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shopcoupon.shop.dto.ShopRequest;
import com.shopcoupon.shop.entity.Shop;

import java.util.List;

public interface ShopService extends IService<Shop> {

    Shop createShop(Long ownerId, ShopRequest request);

    Shop updateShop(Long shopId, Long ownerId, ShopRequest request);

    Shop getShopById(Long shopId);

    List<Shop> listShopsByOwner(Long ownerId);

    void updateShopStatus(Long shopId, Long ownerId, Integer status);
}
