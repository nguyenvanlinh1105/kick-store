# Hướng dẫn Hiện thực hóa Repository Layer - KickVerse

Tài liệu này cung cấp mã nguồn mẫu cho các interface **JPA Repository** trong gói package `com.kick_api.repository` tương ứng với các aggregate của dự án **KickVerse**. 

Các interface này thừa kế từ **`JpaRepository`** của Spring Data JPA để tự động có các phương thức CRUD cơ bản và được bổ sung các phương thức truy vấn tùy biến (Query Methods) phục vụ logic nghiệp vụ.

---

## 1. Danh sách các Repository mẫu

### 1.1. UserRepository.java
Quản lý người dùng, tìm kiếm theo email để phục vụ Login, kiểm tra tính duy nhất của SĐT.
```java
package com.kick_api.repository;

import com.kick_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Tìm kiếm user bằng email để phục vụ login
    Optional<User> findByEmail(String email);
    
    // Tìm kiếm user bằng số điện thoại
    Optional<User> findByPhone(String phone);

    // Kiểm tra email đã tồn tại chưa
    boolean existsByEmail(String email);

    // Kiểm tra số điện thoại đã tồn tại chưa
    boolean existsByPhone(String phone);
}
```

### 1.2. AddressRepository.java
Quản lý địa chỉ nhận hàng, lọc bỏ các địa chỉ đã xóa mềm (`is_deleted = true`).
```java
package com.kick_api.repository;

import com.kick_api.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    
    // Tìm tất cả địa chỉ chưa xóa của 1 user
    List<Address> findAllByUserIdAndIsDeletedFalse(Long userId);

    // Tìm chi tiết 1 địa chỉ chưa xóa của user
    Optional<Address> findByIdAndUserIdAndIsDeletedFalse(Long id, Long userId);

    // Tìm địa chỉ mặc định của user
    Optional<Address> findByUserIdAndIsDefaultTrueAndIsDeletedFalse(Long userId);
}
```

### 1.3. CategoryRepository.java
Quản lý danh mục cây sản phẩm.
```java
package com.kick_api.repository;

import com.kick_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // Tìm các danh mục con trực thuộc danh mục cha
    List<Category> findAllByParentId(Long parentId);

    // Tìm tất cả các danh mục đang hoạt động và sắp xếp theo thứ tự hiển thị
    List<Category> findAllByIsActiveTrueOrderByDisplayOrderAsc();

    // Tìm danh mục theo đường dẫn thân thiện (slug)
    Optional<Category> findBySlug(String slug);
}
```

### 1.4. BrandRepository.java
```java
package com.kick_api.repository;

import com.kick_api.entity.Brand;
import com.kick_api.constant.BrandStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    
    // Lấy danh sách thương hiệu theo trạng thái hoạt động
    List<Brand> findAllByStatus(BrandStatus status);
}
```

### 1.5. ProductRepository.java
Hỗ trợ tìm kiếm, phân trang và lọc sản phẩm.
```java
package com.kick_api.repository;

import com.kick_api.entity.Product;
import com.kick_api.constant.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Tìm sản phẩm theo slug phục vụ trang chi tiết
    Optional<Product> findBySlugAndStatus(String slug, ProductStatus status);

    // Phân trang sản phẩm đang bán
    Page<Product> findAllByStatus(ProductStatus status, Pageable pageable);

    // Phân trang sản phẩm thuộc 1 danh mục cụ thể
    Page<Product> findAllByCategoryIdAndStatus(Long categoryId, ProductStatus status, Pageable pageable);

    // Tìm kiếm sản phẩm theo từ khóa (tên hoặc mô tả)
    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' AND " +
           "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Product> searchProducts(@Param("keyword") String keyword, Pageable pageable);
}
```

### 1.6. ProductVariantRepository.java
Quản lý chi tiết kích cỡ, màu sắc, giá bán và kho của từng SKU.
```java
package com.kick_api.repository;

import com.kick_api.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    // Lấy tất cả biến thể của 1 sản phẩm
    List<ProductVariant> findAllByProductId(Long productId);

    // Tìm biến thể theo mã SKU
    Optional<ProductVariant> findBySku(String sku);

    // Kiểm tra SKU đã tồn tại chưa
    boolean existsBySku(String sku);
}
```

### 1.7. CartRepository.java & CartItemRepository.java
Giỏ hàng và sản phẩm trong giỏ của khách hàng.
```java
// CartRepository.java
package com.kick_api.repository;

import com.kick_api.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    // Tìm giỏ hàng của user đã đăng nhập
    Optional<Cart> findByUserId(Long userId);

    // Tìm giỏ hàng của guest chưa đăng nhập qua session_id
    Optional<Cart> findBySessionId(String sessionId);
}
```

```java
// CartItemRepository.java
package com.kick_api.repository;

import com.kick_api.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    // Tìm sản phẩm cụ thể trong giỏ để tăng/giảm số lượng
    Optional<CartItem> findByCartIdAndVariantId(Long cartId, Long variantId);

    // Xóa sản phẩm khỏi giỏ hàng
    void deleteByCartIdAndVariantId(Long cartId, Long variantId);
}
```

### 1.8. CouponRepository.java & CouponUsageRepository.java
Kiểm tra mã giảm giá và đếm số lần sử dụng của user.
```java
// CouponRepository.java
package com.kick_api.repository;

import com.kick_api.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    
    // Tìm mã coupon để áp dụng vào đơn hàng
    Optional<Coupon> findByCode(String code);
}
```

```java
// CouponUsageRepository.java
package com.kick_api.repository;

import com.kick_api.entity.CouponUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponUsageRepository extends JpaRepository<CouponUsage, Long> {
    
    // Đếm số lần 1 user đã áp dụng mã coupon này (enforce per_user_limit)
    long countByCouponIdAndUserId(Long couponId, Long userId);
}
```

### 1.9. OrderRepository.java & OrderItemRepository.java
Quản lý đơn hàng.
```java
// OrderRepository.java
package com.kick_api.repository;

import com.kick_api.entity.Order;
import com.kick_api.constant.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // Xem lịch sử đơn hàng của 1 user
    Page<Order> findAllByUserId(Long userId, Pageable pageable);

    // Tìm đơn hàng theo mã số đơn (Order Number)
    Optional<Order> findByOrderNo(String orderNo);

    // Tìm các đơn hàng theo trạng thái (dành cho Admin/Staff quản lý)
    Page<Order> findAllByStatus(OrderStatus status, Pageable pageable);
}
```

### 1.10. PaymentRepository.java & RefundRepository.java
```java
// PaymentRepository.java
package com.kick_api.repository;

import com.kick_api.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    // Lấy lịch sử giao dịch thanh toán của một đơn hàng
    List<Payment> findAllByOrderId(Long orderId);
}
```

```java
// RefundRepository.java
package com.kick_api.repository;

import com.kick_api.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
    
    // Lấy thông tin hoàn tiền của đơn hàng
    List<Refund> findAllByOrderId(Long orderId);
}
```

### 1.11. StockTransactionRepository.java
Kiểm soát biến động kho.
```java
package com.kick_api.repository;

import com.kick_api.entity.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {
    
    // Xem lịch sử thay đổi tồn kho của một biến thể (Variant) giảm dần theo thời gian
    List<StockTransaction> findAllByVariantIdOrderByCreatedAtDesc(Long variantId);
}
```

### 1.12. ReviewRepository.java & ReviewImageRepository.java
Quản lý đánh giá sản phẩm.
```java
// ReviewRepository.java
package com.kick_api.repository;

import com.kick_api.entity.Review;
import com.kick_api.constant.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    // Lấy danh sách đánh giá đã duyệt của sản phẩm để hiển thị ở frontend
    Page<Review> findAllByProductIdAndStatus(Long productId, ReviewStatus status, Pageable pageable);

    // Kiểm tra xem user đã từng viết review cho sản phẩm này từ đơn hàng này chưa (tránh spam)
    boolean existsByUserIdAndProductIdAndOrderItemId(Long userId, Long productId, Long orderItemId);
}
```

### 1.13. WishlistRepository.java
```java
package com.kick_api.repository;

import com.kick_api.entity.Wishlist;
import com.kick_api.entity.Wishlist.WishlistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, WishlistId> {
    
    // Lấy danh sách sản phẩm yêu thích của 1 user
    List<Wishlist> findAllByUserId(Long userId);

    // Kiểm tra xem sản phẩm đã có trong wishlist chưa
    boolean existsById(WishlistId id);
}
```

### 1.14. NotificationRepository.java
```java
package com.kick_api.repository;

import com.kick_api.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    // Lấy danh sách thông báo phân trang của user
    Page<Notification> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    // Đếm số thông báo chưa đọc
    long countByUserIdAndIsReadFalse(Long userId);

    // Đánh dấu đọc tất cả thông báo của user
    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.user.id = :userId")
    void markAllAsRead(@Param("userId") Long userId);
}
```

### 1.15. ConversationRepository.java & MessageRepository.java
Quản lý khung chat tư vấn.
```java
// ConversationRepository.java
package com.kick_api.repository;

import com.kick_api.entity.Conversation;
import com.kick_api.constant.ConversationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    
    // Lấy các cuộc chat phân trang của khách hàng
    Page<Conversation> findAllByCustomerId(Long customerId, Pageable pageable);

    // Lấy các cuộc chat phân trang của nhân viên chăm sóc
    Page<Conversation> findAllBySalesManagerId(Long salesManagerId, Pageable pageable);

    // Tìm cuộc hội thoại đang mở (ACTIVE) của khách hàng
    @Query("SELECT c FROM Conversation c WHERE c.customer.id = :customerId AND c.status = 'OPEN'")
    Optional<Conversation> findActiveConversationByCustomerId(@Param("customerId") Long customerId);
}
```

```java
// MessageRepository.java
package com.kick_api.repository;

import com.kick_api.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    // Lấy lịch sử tin nhắn của cuộc hội thoại (sắp xếp tin nhắn mới nhất lên trước)
    Page<Message> findAllByConversationIdOrderBySentAtDesc(Long conversationId, Pageable pageable);
}
```

---

## 2. Quy tắc lập trình chung tại Repository Layer

1.  **Dùng `LAZY` fetching**: Để tối ưu hiệu năng SQL, luôn giữ `FetchType.LAZY` trên các quan hệ `@ManyToOne`. Khi viết câu query cần nạp cả dữ liệu liên quan để tránh lỗi N+1 Query, hãy dùng `JOIN FETCH` trong JPQL:
    ```java
    @Query("SELECT p FROM Product p JOIN FETCH p.brand JOIN FETCH p.category WHERE p.id = :id")
    Optional<Product> findByIdWithDetails(@Param("id") Long id);
    ```
2.  **`@Modifying` cho các lệnh UPDATE/DELETE**: Khi viết các câu `@Query` thực thi lệnh chỉnh sửa/xóa bảng dữ liệu trực tiếp, luôn đi kèm annotation `@Modifying` và `@Transactional` (từ gói Spring):
    ```java
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") UserStatus status);
    ```
3.  **Tránh N+1 query trên Collection**: Hạn chế gọi `cart.getCartItems().size()` nếu collection đó có dung lượng cực lớn. Thay vào đó hãy dùng truy vấn đếm trực tiếp:
    ```java
    @Query("SELECT COUNT(ci) FROM CartItem ci WHERE ci.cart.id = :cartId")
    long countItemsInCart(@Param("cartId") Long cartId);
    ```
