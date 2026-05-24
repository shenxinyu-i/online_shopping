package com.shopcoupon.inventory.controller;

import com.shopcoupon.common.result.Result;
import com.shopcoupon.inventory.dto.ConfirmDeductRequest;
import com.shopcoupon.inventory.dto.LockStockRequest;
import com.shopcoupon.inventory.dto.ReleaseStockRequest;
import com.shopcoupon.inventory.entity.Inventory;
import com.shopcoupon.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{productId}")
    public Result<Inventory> getInventory(@PathVariable Long productId) {
        return Result.success(inventoryService.getInventory(productId));
    }

    @PostMapping("/lock")
    public Result<Void> lockStock(@Valid @RequestBody LockStockRequest request) {
        inventoryService.lockStock(request);
        return Result.success(null);
    }

    @PostMapping("/confirm")
    public Result<Void> confirmDeduct(@Valid @RequestBody ConfirmDeductRequest request) {
        inventoryService.confirmDeduct(request);
        return Result.success(null);
    }

    @PostMapping("/release")
    public Result<Void> releaseStock(@Valid @RequestBody ReleaseStockRequest request) {
        inventoryService.releaseStock(request);
        return Result.success(null);
    }
}
