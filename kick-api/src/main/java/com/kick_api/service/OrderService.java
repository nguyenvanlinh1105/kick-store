package com.kick_api.service;

import com.kick_api.entity.Order;
import com.kick_api.constant.OrderStatus;

public interface OrderService {
    Order checkout(Long userId, Long addressId, String couponCode, String paymentMethod);
    void cancelOrder(Long orderId, String reason);
    void updateOrderStatus(Long orderId, OrderStatus newStatus);
}
