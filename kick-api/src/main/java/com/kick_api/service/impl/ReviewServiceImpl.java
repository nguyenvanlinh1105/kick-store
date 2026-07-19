package com.kick_api.service.impl;

import com.kick_api.entity.*;
import com.kick_api.constant.ReviewStatus;
import com.kick_api.constant.ErrorCode;
import com.kick_api.exception.AppException;
import com.kick_api.repository.*;
import com.kick_api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public Review createReview(Long userId, Long productId, Long orderItemId, int rating, String comment, List<String> imageUrls) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_INPUT, "Không tìm thấy chi tiết đơn hàng!"));

        // Đảm bảo chi tiết đơn hàng thuộc về sản phẩm này
        if (!orderItem.getVariant().getProduct().getId().equals(productId)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Sản phẩm đánh giá không khớp với đơn hàng!");
        }

        // Kiểm tra xem đã review chưa
        if (reviewRepository.existsByUserIdAndProductIdAndOrderItemId(userId, productId, orderItemId)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Bạn đã thực hiện đánh giá cho sản phẩm này rồi!");
        }

        Review review = Review.builder()
                .user(user)
                .product(product)
                .orderItem(orderItem)
                .rating(rating)
                .content(comment)
                .status(ReviewStatus.PENDING)
                .build();

        Review savedReview = reviewRepository.save(review);

        if (imageUrls != null && !imageUrls.isEmpty()) {
            List<ReviewImage> images = new ArrayList<>();
            for (String url : imageUrls) {
                ReviewImage image = ReviewImage.builder()
                        .review(savedReview)
                        .url(url)
                        .build();
                images.add(image);
            }
            reviewImageRepository.saveAll(images);
            savedReview.setImages(images);
        }

        return savedReview;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Review> getProductReviews(Long productId, Pageable pageable) {
        if (!productRepository.existsById(productId)) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        return reviewRepository.findAllByProductIdAndStatus(productId, ReviewStatus.APPROVED, pageable);
    }
}
