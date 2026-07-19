package com.kick_api.repository;

import com.kick_api.entity.Review;
import com.kick_api.constant.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    Page<Review> findAllByProductIdAndStatus(Long productId, ReviewStatus status, Pageable pageable);

    boolean existsByUserIdAndProductIdAndOrderItemId(Long userId, Long productId, Long orderItemId);
}
