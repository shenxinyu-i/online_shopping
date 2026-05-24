package com.shopcoupon.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopcoupon.shop.dto.ShopRequest;
import com.shopcoupon.shop.entity.Shop;
import com.shopcoupon.shop.mapper.ShopMapper;
import com.shopcoupon.shop.service.ShopService;
import com.shopcoupon.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl extends ServiceImpl<ShopMapper,Shop>implements ShopService {

    private final ShopMapper shopMapper;

    @Override
    public Shop createShop(Long ownerId, ShopRequest request) {
        // TODO: 校验商家角色，创建店铺
        throw new BusinessException("创建店铺功能待完善");
    }

    @Override
    public Shop updateShop(Long shopId, Long ownerId, ShopRequest request) {
        // TODO: 校验店铺归属，更新店铺信息
        throw new BusinessException("更新店铺功能待完善");
    }

    @Override
    public Shop getShopById(Long shopId) {
        // TODO: 查询店铺详情
        Shop shop = shopMapper.selectById(shopId);
        if (shop == null) {
            throw new BusinessException(404, "店铺不存在");
        }
        return shop;
    }

    @Override
    public List<Shop> listShopsByOwner(Long ownerId) {
        // TODO: 查询商家名下所有店铺
        throw new BusinessException("店铺列表功能待完善");
    }

    @Override
    public void updateShopStatus(Long shopId, Long ownerId, Integer status) {
        // TODO: 校验归属后更新营业状态
        throw new BusinessException("更新店铺状态功能待完善");
    }
}
