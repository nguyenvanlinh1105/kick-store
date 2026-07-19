package com.kick_api.service;

import com.kick_api.entity.Wishlist;
import java.util.List;

public interface WishlistService {
    
    List<Wishlist> getUserWishlist(Long userId);
    
    void addToWishlist(Long userId, Long productId);
    
    void removeFromWishlist(Long userId, Long productId);
}
