package com.kick_api.entity;

import com.kick_api.constant.DiscountType;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false, length = 20)
    private DiscountType discountType;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal value;

    @Column(name = "min_order", precision = 19, scale = 2)
    private BigDecimal minOrder = BigDecimal.ZERO;

    @Column(name = "max_discount", precision = 19, scale = 2)
    private BigDecimal maxDiscount;

    @Column(name = "start_at", nullable = false)
    private Instant startAt;

    @Column(name = "end_at", nullable = false)
    private Instant endAt;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "per_user_limit", nullable = false)
    private Integer perUserLimit = 1;

    @Column(nullable = false, length = 20)
    private String status = "ACTIVE";
}
