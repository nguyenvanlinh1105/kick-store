package com.kick_api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ReviewRequest {

    @NotNull(message = "Sản phẩm đánh giá không được trống")
    private Long productId;

    @NotNull(message = "Chi tiết đơn hàng không được trống")
    private Long orderItemId;

    @Min(value = 1, message = "Đánh giá thấp nhất là 1 sao")
    @Max(value = 5, message = "Đánh giá cao nhất là 5 sao")
    private int rating;

    @NotBlank(message = "Nội dung đánh giá không được trống")
    private String comment;

    private List<String> imageUrls;
}
