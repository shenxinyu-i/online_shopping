package com.shopcoupon.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shopcoupon.inventory.dto.ConfirmDeductRequest;
import com.shopcoupon.inventory.dto.LockStockRequest;
import com.shopcoupon.inventory.dto.ReleaseStockRequest;
import com.shopcoupon.inventory.entity.Inventory;
import com.shopcoupon.inventory.mapper.InventoryLogMapper;
import com.shopcoupon.inventory.mapper.InventoryMapper;
import com.shopcoupon.inventory.service.InventoryService;
import com.shopcoupon.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryMapper inventoryMapper;
    private final InventoryLogMapper inventoryLogMapper;

    @Override
    public Inventory getInventory(Long productId) {
        Inventory inventory = inventoryMapper.selectOne(new LambdaQueryWrapper<Inventory>()
                .eq(Inventory::getProductId, productId));
        if (inventory == null) {
            throw new BusinessException(404, "库存记录不存在");
        }
        return inventory;
    }

    @Override
    public void lockStock(LockStockRequest request) {
        // TODO: 乐观锁预扣库存，记录 inventory_log (LOCK)
        throw new BusinessException("库存预扣功能待完善");
    }

    @Override
    public void confirmDeduct(ConfirmDeductRequest request) {
        // TODO: 确认扣减 locked_stock，记录 inventory_log (CONFIRM)
        throw new BusinessException("库存确认扣减功能待完善");
    }

    @Override
    public void releaseStock(ReleaseStockRequest request) {
        // TODO: 释放锁定库存，记录 inventory_log (RELEASE)
        throw new BusinessException("库存回补功能待完善");
    }
}
