package com.kick_api.repository;

import com.kick_api.entity.Wishlist;
import com.kick_api.entity.Wishlist.WishlistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, WishlistId> {
    
    List<Wishlist> findAllByUserId(Long userId);

    boolean existsById(WishlistId id);
}
