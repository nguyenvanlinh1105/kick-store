package com.kick_api.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 50)
    private String label;

    @Column(name = "receiver_name", nullable = false, length = 100)
    private String receiverName;

    @Column(nullable = false, length = 10)
    private String phone;

    @Column(nullable = false, length = 100)
    private String province;

    @Column(nullable = false, length = 100)
    private String district;

    @Column(nullable = false, length = 100)
    private String ward;

    @Column(name = "address_detail", nullable = false, length = 255)
    private String addressDetail;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
