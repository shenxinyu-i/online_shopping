package com.shopcoupon.coupon.controller;

import com.shopcoupon.common.constant.CommonConstants;
import com.shopcoupon.common.result.Result;
import com.shopcoupon.coupon.dto.CouponTemplateRequest;
import com.shopcoupon.coupon.entity.CouponTemplate;
import com.shopcoupon.coupon.entity.UserCoupon;
import com.shopcoupon.coupon.service.CouponTemplateService;
import com.shopcoupon.coupon.service.SeckillService;
import com.shopcoupon.coupon.service.UserCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponTemplateService couponTemplateService;
    private final SeckillService seckillService;
    private final UserCouponService userCouponService;

    @PostMapping("/templates")
    public Result<CouponTemplate> createTemplate(@Valid @RequestBody CouponTemplateRequest request) {
        return Result.success(couponTemplateService.createTemplate(request));
    }

    @PutMapping("/templates/{templateId}")
    public Result<CouponTemplate> updateTemplate(
            @PathVariable Long templateId,
            @Valid @RequestBody CouponTemplateRequest request) {
        return Result.success(couponTemplateService.updateTemplate(templateId, request));
    }

    @GetMapping("/templates/{templateId}")
    public Result<CouponTemplate> getTemplate(@PathVariable Long templateId) {
        return Result.success(couponTemplateService.getTemplateById(templateId));
    }

    @GetMapping("/templates")
    public Result<List<CouponTemplate>> listTemplates(@RequestParam Long shopId) {
        return Result.success(couponTemplateService.listTemplates(shopId));
    }

    @PostMapping("/templates/{templateId}/warmup")
    public Result<Void> warmupStock(@PathVariable Long templateId) {
        couponTemplateService.warmupStock(templateId);
        return Result.success(null);
    }

    @PostMapping("/seckill/{templateId}")
    public Result<String> seckill(
            @PathVariable Long templateId,
            @RequestHeader(CommonConstants.USER_ID_HEADER) Long userId) {
        return seckillService.seckillCoupon(templateId, userId);
    }

    @GetMapping("/my")
    public Result<List<UserCoupon>> listMyCoupons(
            @RequestHeader(CommonConstants.USER_ID_HEADER) Long userId,
            @RequestParam(required = false) String status) {
        return Result.success(userCouponService.listMyCoupons(userId, status));
    }
}
