package com.shopcoupon.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shopcoupon.shop.dto.ProductRequest;
import com.shopcoupon.shop.entity.Product;
import com.shopcoupon.shop.mapper.ProductMapper;
import com.shopcoupon.shop.service.ProductService;
import com.shopcoupon.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends ServiceImpl<ProductMapper,Product> implements ProductService {

    private final ProductMapper productMapper;

    @Override
    public Product createProduct(ProductRequest request) {
        // TODO: 校验店铺存在，创建商品
        throw new BusinessException("创建商品功能待完善");
    }

    @Override
    @CacheEvict(value = "productDetail", key = "#productId")
    public Product updateProduct(Long productId, ProductRequest request) {
        // TODO: 更新MySQL后删除Redis缓存（延迟双删可选）
        throw new BusinessException("更新商品功能待完善");
    }

    @Override
    @Cacheable(value = "productDetail", key = "#productId", unless = "#result == null")
    public Product getProductDetail(Long productId) {
        // TODO: Cache-Aside模式，未命中查DB并回写Redis
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new BusinessException(404, "商品不存在");
        }
        return product;
    }

    @Override
    public List<Product> listProducts(Long shopId, String category, String keyword) {
        LambdaQueryWrapper<Product> query = new LambdaQueryWrapper<Product>()
                .eq(shopId != null, Product::getShopId, shopId)
                .eq(category != null && !category.isEmpty(), Product::getCategory, category)
                .like(keyword != null && !keyword.isEmpty(), Product::getName, keyword);
        return productMapper.selectList(query);
    }

    @Override
    @CacheEvict(value = "productDetail", key = "#productId")
    public void updateProductStatus(Long productId, Integer status) {
        // TODO: 上架/下架商品，删除缓存
        throw new BusinessException("更新商品状态功能待完善");
    }
}
