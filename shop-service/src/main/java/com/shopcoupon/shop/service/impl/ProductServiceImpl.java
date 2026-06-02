package com.shopcoupon.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopcoupon.common.exception.BusinessException;
import com.shopcoupon.shop.dto.ProductRequest;
import com.shopcoupon.shop.entity.Product;
import com.shopcoupon.shop.entity.Shop;
import com.shopcoupon.shop.mapper.ProductMapper;
import com.shopcoupon.shop.mapper.ShopMapper;
import com.shopcoupon.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    private final ShopMapper shopMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product createProduct(ProductRequest request) {
        Shop shop = shopMapper.selectById(request.getShopId());
        if (shop == null) {
            throw new BusinessException(404, "店铺不存在");
        }
        if (shop.getStatus() == 0) {
            throw new BusinessException(400, "店铺已歇业，无法创建商品");
        }

        Product product = new Product();
        BeanUtils.copyProperties(request, product);
        product.setStatus(1);

        boolean saved = this.save(product);
        if (!saved) {
            throw new BusinessException(500, "创建商品失败");
        }
        return product;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product updateProduct(Long productId, ProductRequest request) {
        Product existingProduct = this.getById(productId);
        if (existingProduct == null) {
            throw new BusinessException(404, "商品不存在");
        }

        if (request.getShopId() != null && !request.getShopId().equals(existingProduct.getShopId())) {
            Shop newShop = shopMapper.selectById(request.getShopId());
            if (newShop == null) {
                throw new BusinessException(404, "目标店铺不存在");
            }
            if (newShop.getStatus() == 0) {
                throw new BusinessException(400, "目标店铺已歇业");
            }
        }

        BeanUtils.copyProperties(request, existingProduct, getNullPropertyNames(request));

        boolean updated = this.updateById(existingProduct);
        if (!updated) {
            throw new BusinessException(500, "更新商品失败");
        }
        return existingProduct;
    }

    @Override
    public Product getProductDetail(Long productId) {
        Product product = this.getById(productId);
        if (product == null) {
            throw new BusinessException(404, "商品不存在");
        }
        return product;
    }

    @Override
    public List<Product> listProducts(Long shopId, String category, String keyword) {
        LambdaQueryWrapper<Product> query = new LambdaQueryWrapper<Product>()
                .eq(shopId != null, Product::getShopId, shopId)
                .eq(StringUtils.hasText(category), Product::getCategory, category)
                .like(StringUtils.hasText(keyword), Product::getName, keyword)
                .orderByDesc(Product::getCreatedAt);
        return this.list(query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProductStatus(Long productId, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException(400, "状态值无效，请使用0或1");
        }

        Product product = this.getById(productId);
        if (product == null) {
            throw new BusinessException(404, "商品不存在");
        }

        if (status == 1) {
            Shop shop = shopMapper.selectById(product.getShopId());
            if (shop == null || shop.getStatus() == 0) {
                throw new BusinessException(400, "店铺已歇业，无法上架商品");
            }
        }

        boolean updated = this.lambdaUpdate()
                .eq(Product::getId, productId)
                .set(Product::getStatus, status)
                .update();

        if (!updated) {
            throw new BusinessException(500, "更新商品状态失败");
        }
    }

    private static String[] getNullPropertyNames(Object source) {
        try {
            java.beans.PropertyDescriptor[] pds = java.beans.Introspector.getBeanInfo(source.getClass())
                    .getPropertyDescriptors();
            java.util.Set<String> emptyNames = new java.util.HashSet<>();
            for (java.beans.PropertyDescriptor pd : pds) {
                Object srcValue = null;
                try {
                    java.lang.reflect.Method readMethod = pd.getReadMethod();
                    if (readMethod != null) {
                        srcValue = readMethod.invoke(source);
                    }
                } catch (Exception e) {
                    continue;
                }
                if (srcValue == null) {
                    emptyNames.add(pd.getName());
                }
            }
            return emptyNames.toArray(new String[0]);
        } catch (java.beans.IntrospectionException e) {
            return new String[0];
        }
    }
}
