package com.kick_api.controller.customer;

import com.kick_api.dto.ApiResponse;
import com.kick_api.entity.Wishlist;
import com.kick_api.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer/wishlist")
@RequiredArgsConstructor
public class CustomerWishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Wishlist>>> getWishlist(@RequestParam Long userId) {
        List<Wishlist> wishlist = wishlistService.getUserWishlist(userId);
        return ResponseEntity.ok(ApiResponse.success(wishlist));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Void>> addToWishlist(
            @RequestParam Long userId,
            @RequestParam Long productId) {
        wishlistService.addToWishlist(userId, productId);
        return ResponseEntity.ok(ApiResponse.success("Đã thêm sản phẩm vào danh sách yêu thích!", null));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Void>> removeFromWishlist(
            @RequestParam Long userId,
            @RequestParam Long productId) {
        wishlistService.removeFromWishlist(userId, productId);
        return ResponseEntity.ok(ApiResponse.success("Đã xóa sản phẩm khỏi danh sách yêu thích!", null));
    }
}
