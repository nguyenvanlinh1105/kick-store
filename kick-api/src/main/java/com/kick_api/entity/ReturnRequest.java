package com.kick_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "return_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "return_no", nullable = false, unique = true, length = 50)
    private String returnNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "reason_code", nullable = false, length = 50)
    private String reasonCode; // SIZE_FIT, DEFECT, WRONG_ITEM

    @Column(name = "reason_note", columnDefinition = "TEXT")
    private String reasonNote;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED, COMPLETED

    @Column(name = "reviewed_at")
    private Instant reviewedAt;

    @Column(name = "reviewed_by", length = 100)
    private String reviewedBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();
}
