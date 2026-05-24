package com.shopcoupon.inventory.service;

import com.shopcoupon.inventory.dto.ConfirmDeductRequest;
import com.shopcoupon.inventory.dto.LockStockRequest;
import com.shopcoupon.inventory.dto.ReleaseStockRequest;
import com.shopcoupon.inventory.entity.Inventory;

public interface InventoryService {

    Inventory getInventory(Long productId);

    void lockStock(LockStockRequest request);

    void confirmDeduct(ConfirmDeductRequest request);

    void releaseStock(ReleaseStockRequest request);
}
