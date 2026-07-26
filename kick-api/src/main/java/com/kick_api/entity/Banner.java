package com.kick_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "banners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 150)
    private String subtitle;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;

    @Column(name = "cta_link", nullable = false, length = 255)
    @Builder.Default
    private String ctaLink = "/shop";

    @Column(name = "display_order", nullable = false)
    @Builder.Default
    private Integer displayOrder = 0;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    @Builder.Default
    private Instant updatedAt = Instant.now();
}
