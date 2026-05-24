package com.shopcoupon.coupon.service;

import com.shopcoupon.coupon.dto.CouponTemplateRequest;
import com.shopcoupon.coupon.entity.CouponTemplate;
import com.shopcoupon.coupon.entity.UserCoupon;

import java.util.List;

public interface CouponTemplateService {

    CouponTemplate createTemplate(CouponTemplateRequest request);

    CouponTemplate updateTemplate(Long templateId, CouponTemplateRequest request);

    CouponTemplate getTemplateById(Long templateId);

    List<CouponTemplate> listTemplates(Long shopId);

    void warmupStock(Long templateId);
}
