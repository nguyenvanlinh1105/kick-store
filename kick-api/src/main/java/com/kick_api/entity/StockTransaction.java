package com.kick_api.entity;

import com.kick_api.constant.StockTransactionType;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "stock_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariant variant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StockTransactionType type;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(length = 255)
    private String note;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();
}
