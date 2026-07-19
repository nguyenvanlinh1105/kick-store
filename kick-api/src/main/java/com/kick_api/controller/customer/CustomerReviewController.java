package com.kick_api.controller.customer;

import com.kick_api.dto.ApiResponse;
import com.kick_api.dto.ReviewRequest;
import com.kick_api.entity.Review;
import com.kick_api.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer/reviews")
@RequiredArgsConstructor
public class CustomerReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<Review>> createReview(
            @RequestParam Long userId,
            @Valid @RequestBody ReviewRequest request) {
        
        Review review = reviewService.createReview(
                userId,
                request.getProductId(),
                request.getOrderItemId(),
                request.getRating(),
                request.getComment(),
                request.getImageUrls()
        );
        return new ResponseEntity<>(ApiResponse.success("Đăng đánh giá thành công!", review), HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<Page<Review>>> getProductReviews(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Review> reviews = reviewService.getProductReviews(productId, pageable);
        return ResponseEntity.ok(ApiResponse.success(reviews));
    }
}
