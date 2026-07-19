package com.kick_api.controller.customer;

import com.kick_api.dto.ApiResponse;
import com.kick_api.entity.Cart;
import com.kick_api.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer/cart")
@RequiredArgsConstructor
public class CustomerCartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse<Cart>> getCart(@RequestParam Long userId) {
        Cart cart = cartService.getOrCreateCartForUser(userId);
        return ResponseEntity.ok(ApiResponse.success(cart));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Cart>> addItem(
            @RequestParam Long userId,
            @RequestParam Long variantId,
            @RequestParam(defaultValue = "1") int quantity) {
        Cart cart = cartService.addItemToCart(userId, null, variantId, quantity);
        return ResponseEntity.ok(ApiResponse.success("Đã thêm sản phẩm vào giỏ hàng!", cart));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Cart>> updateQuantity(
            @RequestParam Long userId,
            @RequestParam Long variantId,
            @RequestParam int quantity) {
        Cart cart = cartService.updateItemQuantity(userId, null, variantId, quantity);
        return ResponseEntity.ok(ApiResponse.success("Đã cập nhật số lượng giỏ hàng!", cart));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Cart>> removeItem(
            @RequestParam Long userId,
            @RequestParam Long variantId) {
        Cart cart = cartService.removeItemFromCart(userId, null, variantId);
        return ResponseEntity.ok(ApiResponse.success("Đã xóa sản phẩm khỏi giỏ hàng!", cart));
    }

    @PostMapping("/merge")
    public ResponseEntity<ApiResponse<Void>> mergeCart(
            @RequestParam Long userId,
            @RequestParam String sessionId) {
        cartService.mergeCart(userId, sessionId);
        return ResponseEntity.ok(ApiResponse.success("Đã gộp giỏ hàng vãng lai thành công!", null));
    }
}
