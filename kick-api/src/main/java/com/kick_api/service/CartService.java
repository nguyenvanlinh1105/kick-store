package com.kick_api.service;

import com.kick_api.entity.Cart;

public interface CartService {
    
    Cart getOrCreateCartForUser(Long userId);
    
    Cart getOrCreateCartForSession(String sessionId);
    
    Cart addItemToCart(Long userId, String sessionId, Long variantId, int quantity);
    
    Cart updateItemQuantity(Long userId, String sessionId, Long variantId, int quantity);
    
    Cart removeItemFromCart(Long userId, String sessionId, Long variantId);
    
    void mergeCart(Long userId, String sessionId);
}
