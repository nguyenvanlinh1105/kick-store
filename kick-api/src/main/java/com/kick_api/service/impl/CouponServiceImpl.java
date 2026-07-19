package com.kick_api.service.impl;

import com.kick_api.entity.Coupon;
import com.kick_api.constant.ErrorCode;
import com.kick_api.exception.AppException;
import com.kick_api.repository.CouponRepository;
import com.kick_api.repository.CouponUsageRepository;
import com.kick_api.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponUsageRepository couponUsageRepository;

    @Override
    @Transactional(readOnly = true)
    public Coupon validateAndGetCoupon(String code, Long userId, BigDecimal orderTotal) {
        Coupon coupon = couponRepository.findByCode(code.trim())
                .orElseThrow(() -> new AppException(ErrorCode.COUPON_NOT_FOUND));

        if (coupon.getEndAt().isBefore(Instant.now())) {
            throw new AppException(ErrorCode.COUPON_EXPIRED);
        }

        if (coupon.getQuantity() <= 0) {
            throw new AppException(ErrorCode.COUPON_LIMIT_REACHED, "Mã giảm giá đã được sử dụng hết!");
        }

        if (orderTotal.compareTo(coupon.getMinOrder()) < 0) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Giá trị đơn hàng chưa đủ điều kiện tối thiểu để sử dụng coupon này!");
        }

        long usages = couponUsageRepository.countByCouponIdAndUserId(coupon.getId(), userId);
        if (usages >= coupon.getPerUserLimit()) {
            throw new AppException(ErrorCode.COUPON_LIMIT_REACHED, "Bạn đã hết lượt sử dụng mã giảm giá này!");
        }

        return coupon;
    }
}
