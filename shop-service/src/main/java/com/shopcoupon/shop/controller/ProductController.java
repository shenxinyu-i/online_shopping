package com.shopcoupon.shop.controller;

import com.shopcoupon.common.result.Result;
import com.shopcoupon.shop.dto.ProductRequest;
import com.shopcoupon.shop.entity.Product;
import com.shopcoupon.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.IntrospectionException;
import java.util.List;

@RestController
@RequestMapping("/api/shop/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Result<Product> createProduct(@Valid @RequestBody ProductRequest request) {
        return Result.success(productService.createProduct(request));
    }

    @PutMapping("/{productId}")
    public Result<Product> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductRequest request) throws IntrospectionException {
        return Result.success(productService.updateProduct(productId, request));
    }

    @GetMapping("/{productId}")
    public Result<Product> getProductDetail(@PathVariable Long productId) {
        return Result.success(productService.getProductDetail(productId));
    }

    @GetMapping
    public Result<List<Product>> listProducts(
            @RequestParam(required = false) Long shopId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword) {
        return Result.success(productService.listProducts(shopId, category, keyword));
    }

    @PutMapping("/{productId}/status")
    public Result<Void> updateStatus(
            @PathVariable Long productId,
            @RequestParam Integer status) {
        productService.updateProductStatus(productId, status);
        return Result.success(null);
    }
}
