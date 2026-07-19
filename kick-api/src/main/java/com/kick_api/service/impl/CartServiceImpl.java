package com.kick_api.service.impl;

import com.kick_api.entity.Cart;
import com.kick_api.entity.CartItem;
import com.kick_api.entity.ProductVariant;
import com.kick_api.entity.User;
import com.kick_api.constant.ErrorCode;
import com.kick_api.exception.AppException;
import com.kick_api.repository.CartItemRepository;
import com.kick_api.repository.CartRepository;
import com.kick_api.repository.ProductVariantRepository;
import com.kick_api.repository.UserRepository;
import com.kick_api.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductVariantRepository productVariantRepository;

    @Override
    @Transactional
    public Cart getOrCreateCartForUser(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
                    Cart cart = Cart.builder()
                            .user(user)
                            .cartItems(new ArrayList<>())
                            .build();
                    return cartRepository.save(cart);
                });
    }

    @Override
    @Transactional
    public Cart getOrCreateCartForSession(String sessionId) {
        return cartRepository.findBySessionId(sessionId)
                .orElseGet(() -> {
                    Cart cart = Cart.builder()
                            .sessionId(sessionId)
                            .cartItems(new ArrayList<>())
                            .build();
                    return cartRepository.save(cart);
                });
    }

    private Cart getCart(Long userId, String sessionId) {
        if (userId != null) {
            return getOrCreateCartForUser(userId);
        } else if (sessionId != null && !sessionId.trim().isEmpty()) {
            return getOrCreateCartForSession(sessionId);
        }
        throw new AppException(ErrorCode.INVALID_INPUT, "Yêu cầu phải có userId hoặc sessionId để xử lý giỏ hàng!");
    }

    @Override
    @Transactional
    public Cart addItemToCart(Long userId, String sessionId, Long variantId, int quantity) {
        Cart cart = getCart(userId, sessionId);
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));

        if (variant.getAvailableStock() < quantity) {
            throw new AppException(ErrorCode.STOCK_INSUFFICIENT, "Sản phẩm " + variant.getSku() + " chỉ còn " + variant.getAvailableStock() + " sản phẩm!");
        }

        CartItem item = cartItemRepository.findByCartIdAndVariantId(cart.getId(), variantId)
                .orElse(null);

        if (item != null) {
            int newQuantity = item.getQuantity() + quantity;
            if (variant.getAvailableStock() < newQuantity) {
                throw new AppException(ErrorCode.STOCK_INSUFFICIENT, "Không thể thêm vì tổng số lượng vượt quá tồn kho!");
            }
            item.setQuantity(newQuantity);
            cartItemRepository.save(item);
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .variant(variant)
                    .quantity(quantity)
                    .build();
            cartItemRepository.save(newItem);
            cart.getCartItems().add(newItem);
        }

        return cart;
    }

    @Override
    @Transactional
    public Cart updateItemQuantity(Long userId, String sessionId, Long variantId, int quantity) {
        Cart cart = getCart(userId, sessionId);
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));

        CartItem item = cartItemRepository.findByCartIdAndVariantId(cart.getId(), variantId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_INPUT, "Sản phẩm không có trong giỏ hàng!"));

        if (quantity <= 0) {
            cartItemRepository.delete(item);
            cart.getCartItems().remove(item);
        } else {
            if (variant.getAvailableStock() < quantity) {
                throw new AppException(ErrorCode.STOCK_INSUFFICIENT, "Sản phẩm " + variant.getSku() + " chỉ còn " + variant.getAvailableStock() + " sản phẩm!");
            }
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }

        return cart;
    }

    @Override
    @Transactional
    public Cart removeItemFromCart(Long userId, String sessionId, Long variantId) {
        Cart cart = getCart(userId, sessionId);
        CartItem item = cartItemRepository.findByCartIdAndVariantId(cart.getId(), variantId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_INPUT, "Sản phẩm không có trong giỏ hàng!"));

        cartItemRepository.delete(item);
        cart.getCartItems().remove(item);
        return cart;
    }

    @Override
    @Transactional
    public void mergeCart(Long userId, String sessionId) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            return;
        }

        Cart guestCart = cartRepository.findBySessionId(sessionId).orElse(null);
        if (guestCart == null || guestCart.getCartItems().isEmpty()) {
            return;
        }

        Cart userCart = getOrCreateCartForUser(userId);

        for (CartItem guestItem : guestCart.getCartItems()) {
            CartItem userItem = cartItemRepository.findByCartIdAndVariantId(userCart.getId(), guestItem.getVariant().getId())
                    .orElse(null);

            if (userItem != null) {
                int mergedQuantity = userItem.getQuantity() + guestItem.getQuantity();
                // Giới hạn tồn kho khi gộp
                int available = guestItem.getVariant().getAvailableStock();
                userItem.setQuantity(Math.min(mergedQuantity, available));
                cartItemRepository.save(userItem);
            } else {
                CartItem newItem = CartItem.builder()
                        .cart(userCart)
                        .variant(guestItem.getVariant())
                        .quantity(guestItem.getQuantity())
                        .build();
                cartItemRepository.save(newItem);
                userCart.getCartItems().add(newItem);
            }
        }

        cartRepository.delete(guestCart);
    }
}
