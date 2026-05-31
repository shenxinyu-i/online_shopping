package com.shopcoupon.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopcoupon.shop.dto.ShopRequest;
import com.shopcoupon.shop.entity.Shop;
import com.shopcoupon.shop.mapper.ShopMapper;
import com.shopcoupon.shop.service.ShopService;
import com.shopcoupon.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.el.LambdaExpression;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl extends ServiceImpl<ShopMapper,Shop>implements ShopService {

    private final ShopMapper shopMapper;

    @Override
    public Shop createShop(Long ownerId, ShopRequest request) {
        //1参数校验
        if (ownerId == null || ownerId <= 0) {
            throw new BusinessException(400, "店主的ID不能为空");
        }
        if (request == null || !StringUtils.hasText(request.getName())) {
            throw new BusinessException("店铺的名称不能为空");
        }
        //2.检查店铺的名称是否同名（在同一店主下）
        long count = lambdaQuery().eq(Shop::getOwnerId, ownerId)
                .eq(Shop::getName, request.getName())
                .count();
        if (count > 0) {
            throw new BusinessException(404, "您已经存在同名店铺");
        }
        //3.创建店铺
        Shop shop = new Shop();
        BeanUtil.copyProperties(request, shop);
        shop.setOwnerId(ownerId);
        shop.setStatus(1);
        shop.setCreatedAt(LocalDateTime.now());
        shop.setUpdatedAt(LocalDateTime.now());
        boolean isSaved = save(shop);
        if (!isSaved) {
            throw new BusinessException("店铺保存失败");
        }
        return shop;
    }

    @Override
    public Shop updateShop(Long shopId, Long ownerId, ShopRequest request) {
        //1参数校验
        if (shopId == null || shopId <= 0) {
            throw new BusinessException(400, "店铺的id不能为空");
        }
        if (ownerId == null || ownerId <= 0) {
            throw new BusinessException(400, "店主的id不能为空");
        }
        //2查询店铺是否存在
        Shop shop = getById(shopId);
        if (shop == null) {
            throw new BusinessException("店铺不存咋");
        }
        //3校验店铺归属
        if (!shop.getOwnerId().equals(ownerId)) {
            throw new BusinessException(404, "无权操作此店铺");
        }
        //4更行店铺信息
        if (StringUtils.hasText(request.getName())) {
            long count = lambdaQuery().eq(Shop::getOwnerId, ownerId)
                    .eq(Shop::getName, request.getName())
                    .ne(Shop::getId, shopId)
                    .count();
            if (count > 0) {
                throw new BusinessException(409, "已存在同名店铺");
            }
            shop.setName(request.getName());
            //5更新其他字段
            if (StringUtils.hasText(request.getLogoUrl())) {
                shop.setLogoUrl(request.getLogoUrl());
            }
            if (StringUtils.hasText(request.getDescription())) {
                shop.setDescription(request.getDescription());
            }
            shop.setUpdatedAt(LocalDateTime.now());
            boolean isUpdated = updateById(shop);
            if (!isUpdated) {
                throw new BusinessException("店铺更新失败");
            }

        } return shop;
    }


    @Override
    public Shop getShopById(Long shopId) {
        Shop shop = getById(shopId);
        if(shop==null)
        {
            throw new BusinessException(404,"店铺不存在");

        }
        return shop;
    }

    @Override
    public List<Shop> listShopsByOwner(Long ownerId) {
        if(ownerId==null||ownerId<=0)
        {
            throw new BusinessException(404,"店主的ID不能未空");
        }
        return lambdaQuery().eq(Shop::getOwnerId,ownerId)
                .orderByDesc(Shop::getCreatedAt)
                .list();
    }

    @Override
    public void updateShopStatus(Long shopId, Long ownerId, Integer status) {
        // 1.参数校验
        if (shopId == null || shopId <= 0) {
            throw new BusinessException(400, "店铺的id不能为空");
        }
        if (ownerId == null || ownerId <= 0) {
            throw new BusinessException(400, "店主的id不能为空");
        }
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException(400, "状态值不正确，请使用0(歇业)或1(营业)");
        }
        //2查询店铺是否存在
        Shop shop = getById(shopId);
        if(shop==null)

        {
            throw new BusinessException(404,"店铺不存在");
        }
        //4更新状态
        shop.setStatus(status);
        shop.setUpdatedAt(LocalDateTime.now());
        boolean isUpdated=updateById(shop);
        if(!isUpdated)
        {
            throw new BusinessException("店铺状态更新失败");
        }
    }
}

