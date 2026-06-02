package com.shopcoupon.order.feign;

import com.shopcoupon.common.result.Result;
import com.shopcoupon.order.feign.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "shop-service")
public interface ProductClient {

    @GetMapping("/api/shop/products/{productId}")
    Result<ProductDTO> getProductDetail(@PathVariable("productId") Long productId);
}
