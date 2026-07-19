package com.kick_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutRequest {

    @NotNull(message = "Địa chỉ nhận hàng không được trống")
    private Long addressId;

    private String couponCode;

    @NotBlank(message = "Phương thức thanh toán không được trống")
    private String paymentMethod;
}
