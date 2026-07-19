# Hướng dẫn Hiện thực hóa JPA Entity - KickVerse

Tài liệu này cung cấp mã nguồn mẫu cho toàn bộ các lớp **JPA Entity** tương ứng với các bảng cơ sở dữ liệu (Phase 1) của dự án **KickVerse**. 
* Các thực thể (Entity) được đặt trong package `com.kick_api.entity`.
* Các kiểu dữ liệu enum (tránh nhầm lẫn với class ánh xạ DB) được tách ra đặt trong package **`com.kick_api.constant`**.

---

## 1. Các Enums sử dụng trong dự án

Tạo các Java Enum tương ứng trong package `com.kick_api.constant`:

```java
// UserStatus.java
package com.kick_api.constant;
public enum UserStatus { ACTIVE, BLOCKED }

// BrandStatus.java
package com.kick_api.constant;
public enum BrandStatus { ACTIVE, INACTIVE }

// ProductStatus.java
package com.kick_api.constant;
public enum ProductStatus { ACTIVE, INACTIVE, DRAFT }

// VariantStatus.java
package com.kick_api.constant;
public enum VariantStatus { ACTIVE, DISCONTINUED }

// DiscountType.java
package com.kick_api.constant;
public enum DiscountType { PERCENTAGE, FIXED_AMOUNT }

// OrderStatus.java
package com.kick_api.constant;
public enum OrderStatus { PENDING, CONFIRMED, PREPARING, SHIPPING, DELIVERED, CANCELLED, REFUNDED }

// PaymentStatus.java
package com.kick_api.constant;
public enum PaymentStatus { PENDING, PAID, FAILED, REFUNDED }

// PaymentMethod.java
package com.kick_api.constant;
public enum PaymentMethod { COD, VNPAY, MOMO }

// RefundStatus.java
package com.kick_api.constant;
public enum RefundStatus { PENDING, APPROVED, REJECTED, COMPLETED }

// StockTransactionType.java
package com.kick_api.constant;
public enum StockTransactionType { IMPORT, EXPORT, ADJUSTMENT, ORDER_RESERVE, ORDER_RELEASE }

// ReviewStatus.java
package com.kick_api.constant;
public enum ReviewStatus { PENDING, APPROVED, REJECTED }

// NotificationType.java
package com.kick_api.constant;
public enum NotificationType { SYSTEM, ORDER_STATUS, PROMOTION }

// ConversationStatus.java
package com.kick_api.constant;
public enum ConversationStatus { OPEN, CLOSED }

// MessageType.java
package com.kick_api.constant;
public enum MessageType { TEXT, IMAGE, FILE }
```

---

## 2. Mã nguồn chi tiết các JPA Entity

### 2.1. Lớp cấu hình phân quyền và người dùng

#### Role.java
```java
package com.kick_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;
}
```

#### User.java
```java
package com.kick_api.entity;

import com.kick_api.constant.UserStatus;
import jakarta.persistence.*;
import lombok.*;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
```

#### Address.java (Xóa mềm - Soft Delete)
```java
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
```

---

### 2.2. Lớp danh mục sản phẩm (Catalog)

#### Brand.java
```java
package com.kick_api.entity;

import com.kick_api.constant.BrandStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 255)
    private String logo;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BrandStatus status = BrandStatus.ACTIVE;
}
```

#### Category.java
```java
package com.kick_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> subCategories;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "display_order")
    private Integer displayOrder = 0;
}
```

#### Product.java
```java
package com.kick_api.entity;

import com.kick_api.constant.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProductStatus status = ProductStatus.ACTIVE;

    @Column(columnDefinition = "jsonb")
    private String attributes; // Cần dùng thư viện Gson/Jackson để parse, hoặc dùng Custom Hibernate Type Converter
}
```

#### ProductVariant.java
```java
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
```

#### ProductImage.java
```java
package com.kick_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    @Column(nullable = false, length = 255)
    private String url;

    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary = false;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;
}
```

---

### 2.3. Lớp Giỏ Hàng (Cart)

#### Cart.java
```java
package com.kick_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "session_id", length = 100)
    private String sessionId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CartItem> cartItems = new ArrayList<>();
}
```

#### CartItem.java
```java
package com.kick_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_items", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"cart_id", "variant_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariant variant;

    @Column(nullable = false)
    private Integer quantity;
}
```

---

### 2.4. Lớp Khuyến Mãi (Coupons)

#### Coupon.java
```java
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
```

#### CouponUsage.java
```java
package com.kick_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "coupon_usages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "used_at", nullable = false)
    private Instant usedAt = Instant.now();
}
```

---

### 2.5. Lớp Đơn Hàng (Order & Payments)

#### Order.java
```java
package com.kick_api.entity;

import com.kick_api.constant.OrderStatus;
import com.kick_api.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "address_id")
    private Long addressId;

    // Snapshot Address
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "order_no", nullable = false, unique = true, length = 50)
    private String orderNo;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "shipping_fee", nullable = false, precision = 19, scale = 2)
    private BigDecimal shippingFee = BigDecimal.ZERO;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(name = "final_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal finalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OrderStatus status = OrderStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 30)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();
}
```

#### OrderItem.java
```java
package com.kick_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    // Snapshots
    @Column(name = "product_name", nullable = false, length = 150)
    private String productName;

    @Column(name = "variant_sku", nullable = false, length = 50)
    private String variantSku;

    @Column(length = 50)
    private String color;

    @Column(length = 20)
    private String size;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;
}
```

#### Payment.java
```java
package com.kick_api.entity;

import com.kick_api.constant.PaymentMethod;
import com.kick_api.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PaymentMethod method;

    @Column(name = "transaction_id", length = 100)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(name = "paid_at")
    private Instant paidAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();
}
```

---

### 2.6. Lớp Hoàn Tiền & Kho Hàng (Refunds & Stock)

#### Refund.java
```java
package com.kick_api.entity;

import com.kick_api.constant.RefundStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "refunds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(length = 255)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RefundStatus status = RefundStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by")
    private User processedBy;

    @Column(name = "processed_at")
    private Instant processedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();
}
```

#### StockTransaction.java
```java
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
```

---

### 2.7. Lớp Đánh Giá & Wishlist

#### Review.java
```java
package com.kick_api.entity;

import com.kick_api.constant.ReviewStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReviewStatus status = ReviewStatus.PENDING;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();
}
```

#### ReviewImage.java
```java
package com.kick_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(nullable = false, length = 255)
    private String url;
}
```

#### Wishlist.java (Sử dụng Composite Primary Key)
```java
package com.kick_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "wishlist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wishlist {

    @EmbeddedId
    private WishlistId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    // Composite key class
    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class WishlistId implements Serializable {
        @Column(name = "user_id")
        private Long userId;

        @Column(name = "product_id")
        private Long productId;
    }
}
```

---

### 2.8. Lớp Thông Báo & Chat

#### Notification.java
```java
package com.kick_api.entity;

import com.kick_api.constant.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationType type;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();
}
```

#### Conversation.java
```java
package com.kick_api.entity;

import com.kick_api.constant.ConversationStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "conversations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_manager_id")
    private User salesManager;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ConversationStatus status = ConversationStatus.OPEN;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "closed_at")
    private Instant closedAt;
}
```

#### Message.java
```java
package com.kick_api.entity;

import com.kick_api.constant.MessageType;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MessageType type = MessageType.TEXT;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "sent_at", nullable = false)
    private Instant sentAt = Instant.now();
}
```

#### MessageAttachment.java
```java
package com.kick_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "message_attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    @Column(name = "file_url", nullable = false, length = 255)
    private String fileUrl;

    @Column(name = "file_type", length = 50)
    private String fileType;
}
```
