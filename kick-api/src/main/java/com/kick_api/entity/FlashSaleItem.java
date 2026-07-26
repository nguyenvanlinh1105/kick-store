package com.kick_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "flash_sale_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlashSaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private FlashSaleCampaign campaign;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;

    @Column(name = "special_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal specialPrice;

    @Column(name = "allocated_stock", nullable = false)
    @Builder.Default
    private Integer allocatedStock = 10;

    @Column(name = "sold_count", nullable = false)
    @Builder.Default
    private Integer soldCount = 0;
}
