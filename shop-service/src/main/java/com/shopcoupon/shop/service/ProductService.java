package com.shopcoupon.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shopcoupon.shop.dto.ProductRequest;
import com.shopcoupon.shop.entity.Product;

import java.beans.IntrospectionException;
import java.util.List;

public interface ProductService extends IService<Product> {

    Product createProduct(ProductRequest request);

    Product updateProduct(Long productId, ProductRequest request) throws IntrospectionException;

    Product getProductDetail(Long productId);

    List<Product> listProducts(Long shopId, String category, String keyword);

    void updateProductStatus(Long productId, Integer status);
}
