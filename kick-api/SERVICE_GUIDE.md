# Hướng dẫn Hiện thực hóa Service Layer - KickVerse

Tài liệu này hướng dẫn thiết kế và hiện thực hóa tầng **Service (Nghiệp vụ)** của dự án **KickVerse (kick-api)** sử dụng mô hình **Spring Boot 3 + Spring Data JPA**.

Tầng Service chịu trách nhiệm xử lý logic nghiệp vụ, quản lý Transaction (giao dịch DB), xử lý ngoại lệ (Exception), kiểm tra quyền hạn, quản lý bộ nhớ đệm (Cache) và phát hành sự kiện (Events).

---

## 1. Thiết kế tổng quan & Quy chuẩn lập trình

### 1.1. Kiến trúc phân tách Interface và Implementation
Khuyên dùng mô hình tách biệt giữa **Interface** (để định nghĩa các cổng giao tiếp/nghiệp vụ) và class **Implementation** (chứa logic hiện thực hóa chi tiết) để tăng tính linh hoạt và dễ kiểm thử (Unit Test/Mocking).

*   **Interface:** Đặt tại package `com.kick_api.service` (Ví dụ: `ProductService`)
*   **Implementation class:** Đặt tại package `com.kick_api.service.impl` (Ví dụ: `ProductServiceImpl`)

### 1.2. Quản lý Giao dịch với `@Transactional`
*   **Đọc dữ liệu (Read-only):** Nên thiết lập `@Transactional(readOnly = true)` để tối ưu hóa hiệu năng truy vấn của Hibernate (bỏ qua cơ chế kiểm tra biến động/dirty checking).
*   **Ghi dữ liệu (Write):** Bắt buộc dùng `@Transactional` trên các method thêm/sửa/xóa để đảm bảo tính toàn vẹn dữ liệu (Rollback nếu xảy ra lỗi giữa chừng).

### 1.3. Xử lý Exception nghiệp vụ
Không nên trả về trực tiếp mã lỗi hoặc đối tượng null khi xảy ra lỗi nghiệp vụ. Thay vào đó, hãy ném ra (throw) các **Runtime Exception tùy biến** (Custom Exception) để Bộ xử lý ngoại lệ tập trung (Global Exception Handler) tự động chuyển hóa thành mã lỗi HTTP (400, 404, 403...) phù hợp.

Ví dụ các Class Exception tự tạo:
*   `ResourceNotFoundException` (HTTP 404)
*   `BadRequestException` (HTTP 400)
*   `InsufficientStockException` (HTTP 400)

---

## 2. Các Service Cốt lõi & Ví dụ Thực tế

### 2.1. ProductService (Quản lý Sản phẩm & Tồn kho)
Xử lý các logic tìm kiếm sản phẩm và cập nhật kho hàng để tránh bán vượt số lượng tồn kho (Overselling).

#### ProductService.java (Interface)
```java
package com.kick_api.service;

import com.kick_api.entity.Product;
import com.kick_api.entity.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    
    // Tìm chi tiết sản phẩm theo slug
    Product getProductBySlug(String slug);

    // Tìm kiếm và phân trang sản phẩm theo từ khóa
    Page<Product> searchProducts(String keyword, Pageable pageable);

    // Cập nhật số lượng tồn kho (khi có giao dịch hoặc điều chỉnh kho)
    void updateStock(Long variantId, int quantityChange);

    // Giữ kho tạm thời khi khách đặt hàng (tránh oversell trước khi thanh toán)
    void reserveStock(Long variantId, int quantity);

    // Giải phóng kho tạm giữ (khi hủy đơn hàng hoặc hết hạn giữ chỗ)
    void releaseStock(Long variantId, int quantity);
}
```

#### ProductServiceImpl.java (Implementation)
```java
package com.kick_api.service.impl;

import com.kick_api.entity.Product;
import com.kick_api.entity.ProductVariant;
import com.kick_api.entity.StockTransaction;
import com.kick_api.constant.ProductStatus;
import com.kick_api.constant.StockTransactionType;
import com.kick_api.repository.ProductRepository;
import com.kick_api.repository.ProductVariantRepository;
import com.kick_api.repository.StockTransactionRepository;
import com.kick_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductVariantRepository variantRepository;
    private final StockTransactionRepository stockTransactionRepository;

    @Override
    @Transactional(readOnly = true)
    public Product getProductBySlug(String slug) {
        return productRepository.findBySlugAndStatus(slug, ProductStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại hoặc đã ngừng bán!"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return productRepository.findAllByStatus(ProductStatus.ACTIVE, pageable);
        }
        return productRepository.searchProducts(keyword.trim(), pageable);
    }

    @Override
    @Transactional
    public void updateStock(Long variantId, int quantityChange) {
        ProductVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Biến thể sản phẩm không tồn tại!"));
        
        int newAvailableStock = variant.getAvailableStock() + quantityChange;
        if (newAvailableStock < 0) {
            throw new RuntimeException("Số lượng tồn kho không đủ để xuất!");
        }
        
        variant.setAvailableStock(newAvailableStock);
        variantRepository.save(variant);

        // Lưu vết lịch sử biến động kho
        StockTransaction transaction = StockTransaction.builder()
                .variant(variant)
                .type(quantityChange > 0 ? StockTransactionType.IMPORT : StockTransactionType.EXPORT)
                .quantity(Math.abs(quantityChange))
                .note("Thay đổi trực tiếp từ quản lý kho")
                .build();
        stockTransactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void reserveStock(Long variantId, int quantity) {
        ProductVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Biến thể không tồn tại!"));

        if (variant.getAvailableStock() < quantity) {
            throw new RuntimeException("Sản phẩm " + variant.getSku() + " đã hết hàng hoặc không đủ tồn kho!");
        }

        // Trừ kho có thể bán, cộng vào kho tạm giữ
        variant.setAvailableStock(variant.getAvailableStock() - quantity);
        variant.setReservedStock(variant.getReservedStock() + quantity);
        variantRepository.save(variant);

        // Ghi nhận giao dịch kho
        StockTransaction transaction = StockTransaction.builder()
                .variant(variant)
                .type(StockTransactionType.ORDER_RESERVE)
                .quantity(quantity)
                .note("Giữ chỗ cho đơn hàng mới")
                .build();
        stockTransactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void releaseStock(Long variantId, int quantity) {
        ProductVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Biến thể không tồn tại!"));

        // Trả lại kho từ kho tạm giữ sang kho có thể bán
        int newReserved = Math.max(0, variant.getReservedStock() - quantity);
        variant.setReservedStock(newReserved);
        variant.setAvailableStock(variant.getAvailableStock() + quantity);
        variantRepository.save(variant);

        // Ghi nhận giao dịch kho
        StockTransaction transaction = StockTransaction.builder()
                .variant(variant)
                .type(StockTransactionType.ORDER_RELEASE)
                .quantity(quantity)
                .note("Giải phóng kho tạm giữ do hủy đơn hàng")
                .build();
        stockTransactionRepository.save(transaction);
    }
}
```

---

### 2.2. OrderService (Quy trình Đặt hàng & Tính toán Thanh toán)
Logic checkout rất nhạy cảm và phức tạp, cần thực hiện đồng thời nhiều bước kiểm tra và áp dụng Transaction.

#### OrderService.java (Interface)
```java
package com.kick_api.service;

import com.kick_api.entity.Order;
import com.kick_api.constant.OrderStatus;

public interface OrderService {

    // Tiến hành checkout và tạo đơn hàng
    Order checkout(Long userId, Long addressId, String couponCode, String paymentMethod);

    // Hủy đơn hàng và giải phóng kho
    void cancelOrder(Long orderId, String reason);

    // Cập nhật trạng thái đơn hàng (Dành cho Admin/Staff vận chuyển)
    void updateOrderStatus(Long orderId, OrderStatus newStatus);
}
```

#### OrderServiceImpl.java (Implementation)
```java
package com.kick_api.service.impl;

import com.kick_api.entity.*;
import com.kick_api.constant.*;
import com.kick_api.repository.*;
import com.kick_api.service.OrderService;
import com.kick_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final CouponRepository couponRepository;
    private final CouponUsageRepository couponUsageRepository;
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Override
    @Transactional
    public Order checkout(Long userId, Long addressId, String couponCode, String paymentMethod) {
        // 1. Lấy thông tin giỏ hàng của User
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Giỏ hàng trống!"));
        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Giỏ hàng không có sản phẩm để đặt hàng!");
        }

        // 2. Lấy thông tin địa chỉ giao hàng và làm snapshot
        Address address = addressRepository.findByIdAndUserIdAndIsDeletedFalse(addressId, userId)
                .orElseThrow(() -> new RuntimeException("Địa chỉ giao hàng không hợp lệ!"));

        // 3. Tính tổng tiền sản phẩm trong giỏ
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem item : cart.getCartItems()) {
            BigDecimal itemPrice = item.getVariant().getPrice();
            totalAmount = totalAmount.add(itemPrice.multiply(BigDecimal.valueOf(item.getQuantity())));
            
            // Giữ chỗ kho (reserve stock) cho từng sản phẩm
            productService.reserveStock(item.getVariant().getId(), item.getQuantity());
        }

        // 4. Kiểm tra và áp dụng mã giảm giá (nếu có)
        BigDecimal discount = BigDecimal.ZERO;
        Coupon appliedCoupon = null;
        if (couponCode != null && !couponCode.trim().isEmpty()) {
            appliedCoupon = couponRepository.findByCode(couponCode.trim())
                    .orElseThrow(() -> new RuntimeException("Mã giảm giá không hợp lệ!"));
            
            // Validate hạn dùng, số lượng, giá trị tối thiểu đơn hàng...
            if (appliedCoupon.getEndAt().isBefore(Instant.now())) {
                throw new RuntimeException("Mã giảm giá đã hết hạn!");
            }
            if (appliedCoupon.getQuantity() <= 0) {
                throw new RuntimeException("Mã giảm giá đã được sử dụng hết!");
            }
            if (totalAmount.compareTo(appliedCoupon.getMinOrder()) < 0) {
                throw new RuntimeException("Đơn hàng chưa đạt giá trị tối thiểu để dùng mã này!");
            }

            // Kiểm tra số lần sử dụng của user
            long usages = couponUsageRepository.countByCouponIdAndUserId(appliedCoupon.getId(), userId);
            if (usages >= appliedCoupon.getPerUserLimit()) {
                throw new RuntimeException("Bạn đã dùng hết số lần cho phép của mã giảm giá này!");
            }

            // Tính số tiền giảm giá
            if (appliedCoupon.getDiscountType() == DiscountType.PERCENTAGE) {
                BigDecimal calculatedDiscount = totalAmount.multiply(appliedCoupon.getValue().divide(BigDecimal.valueOf(100)));
                discount = appliedCoupon.getMaxDiscount() != null 
                        ? calculatedDiscount.min(appliedCoupon.getMaxDiscount()) 
                        : calculatedDiscount;
            } else {
                discount = appliedCoupon.getValue().min(totalAmount);
            }
        }

        BigDecimal shippingFee = BigDecimal.valueOf(30000); // Mặc định phí ship (có thể tính động qua API vận chuyển)
        BigDecimal finalAmount = totalAmount.add(shippingFee).subtract(discount);

        // 5. Tạo đơn hàng và làm Snapshot thông tin địa chỉ
        Order order = Order.builder()
                .user(cart.getUser())
                .addressId(addressId)
                .receiverName(address.getReceiverName())
                .phone(address.getPhone())
                .province(address.getProvince())
                .district(address.getDistrict())
                .ward(address.getWard())
                .addressDetail(address.getAddressDetail())
                .coupon(appliedCoupon)
                .orderNo("KV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .totalAmount(totalAmount)
                .shippingFee(shippingFee)
                .discount(discount)
                .finalAmount(finalAmount)
                .status(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        // Tạo chi tiết đơn hàng (Snapshot sản phẩm)
        order.setOrderItems(cart.getCartItems().stream().map(item -> OrderItem.builder()
                .order(order)
                .variant(item.getVariant())
                .productName(item.getVariant().getProduct().getName())
                .variantSku(item.getVariant().getSku())
                .color(item.getVariant().getColor())
                .size(item.getVariant().getSize())
                .price(item.getVariant().getPrice())
                .quantity(item.getQuantity())
                .build()).collect(Collectors.toList()));

        Order savedOrder = orderRepository.save(order);

        // 6. Ghi nhận lịch sử dùng mã giảm giá
        if (appliedCoupon != null) {
            CouponUsage usage = CouponUsage.builder()
                    .coupon(appliedCoupon)
                    .user(cart.getUser())
                    .order(savedOrder)
                    .build();
            couponUsageRepository.save(usage);

            // Giảm số lượng coupon còn lại
            appliedCoupon.setQuantity(appliedCoupon.getQuantity() - 1);
            couponRepository.save(appliedCoupon);
        }

        // 7. Xóa sạch giỏ hàng sau khi đặt hàng thành công
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId, String reason) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng!"));

        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.CONFIRMED) {
            throw new RuntimeException("Chỉ có thể hủy đơn hàng ở trạng thái chờ duyệt hoặc đã xác nhận!");
        }

        // Hủy đơn hàng
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        // Trả lại kho (release stock) cho các sản phẩm trong đơn hàng
        for (OrderItem item : order.getOrderItems()) {
            if (item.getVariant() != null) {
                productService.releaseStock(item.getVariant().getId(), item.getQuantity());
            }
        }
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng!"));

        // Nếu chuyển sang trạng thái chuẩn bị xong (PREPARING) hoặc vận chuyển (SHIPPING)
        // Chúng ta chính thức trừ số lượng kho tạm giữ (reserved stock) sang xuất kho hoàn toàn
        if (newStatus == OrderStatus.PREPARING && order.getStatus() == OrderStatus.CONFIRMED) {
            for (OrderItem item : order.getOrderItems()) {
                if (item.getVariant() != null) {
                    ProductVariant variant = item.getVariant();
                    // Trừ số lượng tạm giữ
                    variant.setReservedStock(Math.max(0, variant.getReservedStock() - item.getQuantity()));
                }
            }
        }

        order.setStatus(newStatus);
        orderRepository.save(order);
    }
}
```

---

### 2.3. Xử lý Logic Xóa mềm (Soft Delete) trong Service
Vì dự án thống nhất không dùng `@SQLDelete` / `@SQLRestriction` để code tường minh, tầng Service chịu trách nhiệm xử lý logic như sau:

```java
// AddressServiceImpl.java (Ví dụ trích đoạn logic xóa mềm địa chỉ)
@Override
@Transactional
public void deleteAddress(Long addressId, Long userId) {
    Address address = addressRepository.findByIdAndUserIdAndIsDeletedFalse(addressId, userId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ!"));

    // Bước 1: Gán cờ isDeleted = true
    address.setDeleted(true);

    // Bước 2: Nếu địa chỉ bị xóa đang là mặc định, hủy trạng thái mặc định của nó
    if (address.isDefault()) {
        address.setDefault(false);
    }

    // Bước 3: Lưu lại cập nhật
    addressRepository.save(address);
}
```
