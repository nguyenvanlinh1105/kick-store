package com.kick_api.entity;

import com.kick_api.constant.VariantStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product_variants", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"product_id", "color", "size"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    @Column(length = 50)
    private String color;

    @Column(length = 20)
    private String size;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "available_stock", nullable = false)
    private Integer availableStock = 0;

    @Column(name = "reserved_stock", nullable = false)
    private Integer reservedStock = 0;

    @Column(precision = 10, scale = 2)
    private BigDecimal weight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VariantStatus status = VariantStatus.ACTIVE;
}
