package com.kick_api.service.impl;

import com.kick_api.entity.*;
import com.kick_api.constant.*;
import com.kick_api.repository.*;
import com.kick_api.exception.AppException;
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
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
        if (cart.getCartItems().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Giỏ hàng không có sản phẩm nào để thanh toán!");
        }

        Address address = addressRepository.findByIdAndUserIdAndIsDeletedFalse(addressId, userId)
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem item : cart.getCartItems()) {
            BigDecimal itemPrice = item.getVariant().getPrice();
            totalAmount = totalAmount.add(itemPrice.multiply(BigDecimal.valueOf(item.getQuantity())));
            productService.reserveStock(item.getVariant().getId(), item.getQuantity());
        }

        BigDecimal discount = BigDecimal.ZERO;
        Coupon appliedCoupon = null;
        if (couponCode != null && !couponCode.trim().isEmpty()) {
            appliedCoupon = couponRepository.findByCode(couponCode.trim())
                    .orElseThrow(() -> new AppException(ErrorCode.COUPON_NOT_FOUND));
            
            if (appliedCoupon.getEndAt().isBefore(Instant.now())) {
                throw new AppException(ErrorCode.COUPON_EXPIRED);
            }
            if (appliedCoupon.getQuantity() <= 0) {
                throw new AppException(ErrorCode.COUPON_LIMIT_REACHED, "Mã giảm giá đã hết lượt sử dụng!");
            }
            if (totalAmount.compareTo(appliedCoupon.getMinOrder()) < 0) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Đơn hàng chưa đạt giá trị tối thiểu để áp dụng mã giảm giá!");
            }

            long usages = couponUsageRepository.countByCouponIdAndUserId(appliedCoupon.getId(), userId);
            if (usages >= appliedCoupon.getPerUserLimit()) {
                throw new AppException(ErrorCode.COUPON_LIMIT_REACHED);
            }

            if (appliedCoupon.getDiscountType() == DiscountType.PERCENTAGE) {
                BigDecimal calculatedDiscount = totalAmount.multiply(appliedCoupon.getValue().divide(BigDecimal.valueOf(100)));
                discount = appliedCoupon.getMaxDiscount() != null 
                        ? calculatedDiscount.min(appliedCoupon.getMaxDiscount()) 
                        : calculatedDiscount;
            } else {
                discount = appliedCoupon.getValue().min(totalAmount);
            }
        }

        BigDecimal shippingFee = BigDecimal.valueOf(30000);
        BigDecimal finalAmount = totalAmount.add(shippingFee).subtract(discount);

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

        if (appliedCoupon != null) {
            CouponUsage usage = CouponUsage.builder()
                    .coupon(appliedCoupon)
                    .user(cart.getUser())
                    .order(savedOrder)
                    .build();
            couponUsageRepository.save(usage);

            appliedCoupon.setQuantity(appliedCoupon.getQuantity() - 1);
            couponRepository.save(appliedCoupon);
        }

        cart.getCartItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId, String reason) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.CONFIRMED) {
            throw new AppException(ErrorCode.ORDER_STATUS_INVALID, "Chỉ có thể hủy đơn hàng ở trạng thái chờ duyệt hoặc đã xác nhận!");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

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
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (newStatus == OrderStatus.PREPARING && order.getStatus() == OrderStatus.CONFIRMED) {
            for (OrderItem item : order.getOrderItems()) {
                if (item.getVariant() != null) {
                    ProductVariant variant = item.getVariant();
                    variant.setReservedStock(Math.max(0, variant.getReservedStock() - item.getQuantity()));
                }
            }
        }

        order.setStatus(newStatus);
        orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<Order> getUserOrders(Long userId, org.springframework.data.domain.Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderDetails(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        if (!order.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Không có quyền truy cập thông tin đơn hàng này!");
        }
        return order;
    }

    @Override
    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<Order> getOrdersByStatus(OrderStatus status, org.springframework.data.domain.Pageable pageable) {
        return orderRepository.findAllByStatus(status, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<Order> getAllOrders(org.springframework.data.domain.Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
}
