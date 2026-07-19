package com.kick_api.service;

import com.kick_api.entity.Order;
import com.kick_api.constant.OrderStatus;

public interface OrderService {
    Order checkout(Long userId, Long addressId, String couponCode, String paymentMethod);
    void cancelOrder(Long orderId, String reason);
    void updateOrderStatus(Long orderId, OrderStatus newStatus);
    org.springframework.data.domain.Page<Order> getUserOrders(Long userId, org.springframework.data.domain.Pageable pageable);
    Order getOrderDetails(Long userId, Long orderId);
    org.springframework.data.domain.Page<Order> getOrdersByStatus(OrderStatus status, org.springframework.data.domain.Pageable pageable);
    org.springframework.data.domain.Page<Order> getAllOrders(org.springframework.data.domain.Pageable pageable);
}
