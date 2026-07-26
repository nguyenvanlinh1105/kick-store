package com.kick_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "stock_import_receipts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockImportReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;

    @Column(nullable = false, length = 50)
    private String sku;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "import_unit_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal importUnitPrice;

    @Column(name = "supplier_name", nullable = false, length = 150)
    private String supplierName;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();

    @Column(name = "created_by", length = 100)
    private String createdBy;
}
