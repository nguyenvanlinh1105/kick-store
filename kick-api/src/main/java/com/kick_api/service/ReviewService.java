package com.kick_api.service;

import com.kick_api.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ReviewService {
    
    Review createReview(Long userId, Long productId, Long orderItemId, int rating, String comment, List<String> imageUrls);
    
    Page<Review> getProductReviews(Long productId, Pageable pageable);
}
