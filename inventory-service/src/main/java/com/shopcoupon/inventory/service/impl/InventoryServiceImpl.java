package com.shopcoupon.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shopcoupon.inventory.dto.ConfirmDeductRequest;
import com.shopcoupon.inventory.dto.LockStockRequest;
import com.shopcoupon.inventory.dto.ReleaseStockRequest;
import com.shopcoupon.inventory.dto.SetStockRequest;
import com.shopcoupon.inventory.entity.Inventory;
import com.shopcoupon.inventory.entity.InventoryLog;
import com.shopcoupon.inventory.mapper.InventoryLogMapper;
import com.shopcoupon.inventory.mapper.InventoryMapper;
import com.shopcoupon.inventory.service.InventoryService;
import com.shopcoupon.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryMapper inventoryMapper;
    private final InventoryLogMapper inventoryLogMapper;

    private static final int MAX_RETRY = 3;

    @Override
    public Inventory getInventory(Long productId) {
        Inventory inventory = inventoryMapper.selectOne(new LambdaQueryWrapper<Inventory>()
                .eq(Inventory::getProductId, productId));
        if (inventory == null) {
            // 如果库存记录不存在，自动创建一条
            inventory = new Inventory();
            inventory.setProductId(productId);
            inventory.setTotalStock(0);
            inventory.setAvailableStock(0);
            inventory.setLockedStock(0);
            inventory.setVersion(0);
            inventory.setCreatedAt(LocalDateTime.now());
            inventory.setUpdatedAt(LocalDateTime.now());
            inventoryMapper.insert(inventory);
        }
        return inventory;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lockStock(LockStockRequest request) {
        // 获取当前库存（带版本号用于乐观锁）
        Inventory inventory = getInventory(request.getProductId());
        int currentVersion = inventory.getVersion();

        // 乐观锁重试机制
        for (int i = 0; i < MAX_RETRY; i++) {
            int rows = inventoryMapper.lockStock(
                    request.getProductId(),
                    request.getCount(),
                    currentVersion);
            if (rows > 0) {
                // 预扣成功，记录日志
                Inventory afterInventory = inventoryMapper.selectOne(
                        new LambdaQueryWrapper<Inventory>()
                                .eq(Inventory::getProductId, request.getProductId()));
                saveInventoryLog(request.getProductId(), request.getOrderNo(), "LOCK",
                        request.getCount(),
                        inventory.getAvailableStock(),
                        afterInventory != null ? afterInventory.getAvailableStock() : 0,
                        inventory.getLockedStock(),
                        afterInventory != null ? afterInventory.getLockedStock() : 0);
                log.info("库存预扣成功: productId={}, orderNo={}, count={}",
                        request.getProductId(), request.getOrderNo(), request.getCount());
                return;
            }

            // 乐观锁冲突，重试前重新读取库存和版本号
            if (i < MAX_RETRY - 1) {
                inventory = getInventory(request.getProductId());
                if (inventory.getAvailableStock() < request.getCount()) {
                    throw new BusinessException("库存不足，无法锁定");
                }
                currentVersion = inventory.getVersion();
                log.warn("库存预扣乐观锁冲突，第{}次重试: productId={}", i + 1, request.getProductId());
            }
        }

        throw new BusinessException("库存预扣失败，系统繁忙，请稍后重试");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmDeduct(ConfirmDeductRequest request) {
        // 获取当前库存（带版本号用于乐观锁）
        Inventory inventory = getInventory(request.getProductId());
        int currentVersion = inventory.getVersion();

        // 乐观锁重试机制
        for (int i = 0; i < MAX_RETRY; i++) {
            int rows = inventoryMapper.confirmDeduct(
                    request.getProductId(),
                    request.getCount(),
                    currentVersion);
            if (rows > 0) {
                // 确认扣减成功，记录日志
                Inventory afterInventory = inventoryMapper.selectOne(
                        new LambdaQueryWrapper<Inventory>()
                                .eq(Inventory::getProductId, request.getProductId()));
                saveInventoryLog(request.getProductId(), request.getOrderNo(), "CONFIRM",
                        request.getCount(),
                        inventory.getLockedStock(),
                        afterInventory != null ? afterInventory.getLockedStock() : 0,
                        inventory.getTotalStock(),
                        afterInventory != null ? afterInventory.getTotalStock() : 0);
                log.info("库存确认扣减成功: productId={}, orderNo={}, count={}",
                        request.getProductId(), request.getOrderNo(), request.getCount());
                return;
            }

            // 乐观锁冲突，重试
            if (i < MAX_RETRY - 1) {
                inventory = getInventory(request.getProductId());
                if (inventory.getLockedStock() < request.getCount()) {
                    throw new BusinessException("锁定库存不足，无法确认扣减");
                }
                currentVersion = inventory.getVersion();
                log.warn("库存确认扣减乐观锁冲突，第{}次重试: productId={}", i + 1, request.getProductId());
            }
        }

        throw new BusinessException("库存确认扣减失败，系统繁忙，请稍后重试");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void releaseStock(ReleaseStockRequest request) {
        // 获取当前库存（带版本号用于乐观锁）
        Inventory inventory = getInventory(request.getProductId());
        int currentVersion = inventory.getVersion();

        // 乐观锁重试机制
        for (int i = 0; i < MAX_RETRY; i++) {
            int rows = inventoryMapper.releaseStock(
                    request.getProductId(),
                    request.getCount(),
                    currentVersion);
            if (rows > 0) {
                // 释放成功，记录日志
                Inventory afterInventory = inventoryMapper.selectOne(
                        new LambdaQueryWrapper<Inventory>()
                                .eq(Inventory::getProductId, request.getProductId()));
                saveInventoryLog(request.getProductId(), request.getOrderNo(), "RELEASE",
                        request.getCount(),
                        inventory.getLockedStock(),
                        afterInventory != null ? afterInventory.getLockedStock() : 0,
                        inventory.getAvailableStock(),
                        afterInventory != null ? afterInventory.getAvailableStock() : 0);
                log.info("库存释放成功: productId={}, orderNo={}, count={}",
                        request.getProductId(), request.getOrderNo(), request.getCount());
                return;
            }

            // 乐观锁冲突，重试
            if (i < MAX_RETRY - 1) {
                inventory = getInventory(request.getProductId());
                if (inventory.getLockedStock() < request.getCount()) {
                    throw new BusinessException("锁定库存不足，无法释放");
                }
                currentVersion = inventory.getVersion();
                log.warn("库存释放乐观锁冲突，第{}次重试: productId={}", i + 1, request.getProductId());
            }
        }

        throw new BusinessException("库存释放失败，系统繁忙，请稍后重试");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setStock(SetStockRequest request) {
        Inventory inventory = inventoryMapper.selectOne(new LambdaQueryWrapper<Inventory>()
                .eq(Inventory::getProductId, request.getProductId()));
        if (inventory == null) {
            // 新建库存记录
            inventory = new Inventory();
            inventory.setProductId(request.getProductId());
            inventory.setTotalStock(request.getStock());
            inventory.setAvailableStock(request.getStock());
            inventory.setLockedStock(0);
            inventory.setVersion(0);
            inventory.setCreatedAt(LocalDateTime.now());
            inventory.setUpdatedAt(LocalDateTime.now());
            inventoryMapper.insert(inventory);
        } else {
            // 更新库存：总库存 = 新设值，可用 = 新设值 - 已锁定
            int newAvailable = request.getStock() - inventory.getLockedStock();
            if (newAvailable < 0) {
                throw new BusinessException("库存不能小于已锁定数量(" + inventory.getLockedStock() + ")");
            }
            inventory.setTotalStock(request.getStock());
            inventory.setAvailableStock(newAvailable);
            inventory.setUpdatedAt(LocalDateTime.now());
            inventoryMapper.updateById(inventory);
        }
        log.info("库存设置成功: productId={}, stock={}", request.getProductId(), request.getStock());
    }

    /**
     * 保存库存变更日志
     */
    private void saveInventoryLog(Long productId, String orderNo, String changeType,
                                   Integer changeCount,
                                   Integer beforeAvailable, Integer afterAvailable,
                                   Integer beforeLocked, Integer afterLocked) {
        InventoryLog logEntry = new InventoryLog();
        logEntry.setProductId(productId);
        logEntry.setOrderNo(orderNo);
        logEntry.setChangeType(changeType);
        logEntry.setChangeCount(changeCount);
        logEntry.setBeforeAvailable(beforeAvailable);
        logEntry.setAfterAvailable(afterAvailable);
        logEntry.setBeforeLocked(beforeLocked);
        logEntry.setAfterLocked(afterLocked);
        logEntry.setCreatedAt(LocalDateTime.now());
        inventoryLogMapper.insert(logEntry);
    }
}
