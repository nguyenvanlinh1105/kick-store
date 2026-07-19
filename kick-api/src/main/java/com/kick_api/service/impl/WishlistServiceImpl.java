package com.kick_api.service.impl;

import com.kick_api.entity.Wishlist;
import com.kick_api.entity.Wishlist.WishlistId;
import com.kick_api.entity.User;
import com.kick_api.entity.Product;
import com.kick_api.constant.ErrorCode;
import com.kick_api.exception.AppException;
import com.kick_api.repository.WishlistRepository;
import com.kick_api.repository.UserRepository;
import com.kick_api.repository.ProductRepository;
import com.kick_api.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Wishlist> getUserWishlist(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return wishlistRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public void addToWishlist(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        WishlistId key = new WishlistId(userId, productId);
        if (wishlistRepository.existsById(key)) {
            return; // Đã nằm trong danh sách yêu thích
        }

        Wishlist wishlist = Wishlist.builder()
                .id(key)
                .user(user)
                .product(product)
                .createdAt(Instant.now())
                .build();

        wishlistRepository.save(wishlist);
    }

    @Override
    @Transactional
    public void removeFromWishlist(Long userId, Long productId) {
        WishlistId key = new WishlistId(userId, productId);
        if (!wishlistRepository.existsById(key)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Sản phẩm không có trong danh sách yêu thích của người dùng!");
        }
        wishlistRepository.deleteById(key);
    }
}
