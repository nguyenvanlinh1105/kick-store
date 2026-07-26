package com.kick_api.entity;

import com.kick_api.constant.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;

    @Column(unique = true, length = 10)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus status = UserStatus.ACTIVE;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String tier = "BRONZE";

    @Column(nullable = false)
    @Builder.Default
    private Integer points = 0;

    @Column(name = "total_spent", nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal totalSpent = BigDecimal.ZERO;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    // Refresh Token lưu trực tiếp trong bảng users
    @JsonIgnore
    @Column(name = "refresh_token", length = 255)
    private String refreshToken;

    @JsonIgnore
    @Column(name = "refresh_token_expiry")
    private Instant refreshTokenExpiry;
}
