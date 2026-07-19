package com.kick_api.repository;

import com.kick_api.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    Optional<CartItem> findByCartIdAndVariantId(Long cartId, Long variantId);

    void deleteByCartIdAndVariantId(Long cartId, Long variantId);
}
