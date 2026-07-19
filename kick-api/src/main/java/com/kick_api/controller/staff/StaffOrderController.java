package com.kick_api.controller.staff;

import com.kick_api.dto.ApiResponse;
import com.kick_api.entity.Order;
import com.kick_api.constant.OrderStatus;
import com.kick_api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/staff/orders")
@RequiredArgsConstructor
public class StaffOrderController {

    private final OrderService orderService;

    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<Void>> updateStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(ApiResponse.success("Đã cập nhật trạng thái đơn hàng thành công!", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Order>>> getAllOrders(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Order> orders = status != null 
                ? orderService.getOrdersByStatus(status, pageable)
                : orderService.getAllOrders(pageable);
                
        return ResponseEntity.ok(ApiResponse.success(orders));
    }
}
