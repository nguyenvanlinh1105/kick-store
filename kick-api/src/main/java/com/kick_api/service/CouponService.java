package com.kick_api.service;

import com.kick_api.entity.Coupon;
import java.math.BigDecimal;

public interface CouponService {
    
    Coupon validateAndGetCoupon(String code, Long userId, BigDecimal orderTotal);
}
