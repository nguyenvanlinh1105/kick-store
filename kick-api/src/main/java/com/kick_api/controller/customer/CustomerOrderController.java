package com.kick_api.controller.customer;

import com.kick_api.dto.ApiResponse;
import com.kick_api.dto.CheckoutRequest;
import com.kick_api.entity.Order;
import com.kick_api.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer/orders")
@RequiredArgsConstructor
public class CustomerOrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<Order>> checkout(
            @RequestParam Long userId,
            @Valid @RequestBody CheckoutRequest request) {
        
        Order order = orderService.checkout(
                userId, 
                request.getAddressId(), 
                request.getCouponCode(), 
                request.getPaymentMethod()
        );
        return new ResponseEntity<>(ApiResponse.success("Đặt hàng thành công!", order), HttpStatus.CREATED);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
            @PathVariable Long orderId,
            @RequestParam String reason) {
        orderService.cancelOrder(orderId, reason);
        return ResponseEntity.ok(ApiResponse.success("Đã yêu cầu hủy đơn hàng!", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Order>>> getUserOrders(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Order> orders = orderService.getUserOrders(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Order>> getOrderDetails(
            @RequestParam Long userId,
            @PathVariable Long orderId) {
        Order order = orderService.getOrderDetails(userId, orderId);
        return ResponseEntity.ok(ApiResponse.success(order));
    }
}
