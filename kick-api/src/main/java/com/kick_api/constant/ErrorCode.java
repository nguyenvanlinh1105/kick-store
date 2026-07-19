package com.kick_api.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter

public enum ErrorCode {
    INVALID_INPUT("INVALID_PARAMETER", HttpStatus.BAD_REQUEST, "Dữ liệu đầu vào không hợp lệ!"),
    INTERNAL_ERROR("ERROR_SYSTEM", HttpStatus.INTERNAL_SERVER_ERROR, "Server Error!"),
    
    USER_NOT_FOUND("ERR_USER_001", HttpStatus.NOT_FOUND, "Người dùng không tồn tại"),
    ADDRESS_NOT_FOUND("ERR_ADDR_001", HttpStatus.NOT_FOUND, "Địa chỉ không tồn tại"),
    CATEGORY_NOT_FOUND("ERR_CAT_001", HttpStatus.NOT_FOUND, "Danh mục không tồn tại"),
    BRAND_NOT_FOUND("ERR_BRND_001", HttpStatus.NOT_FOUND, "Thương hiệu không tồn tại"),
    PRODUCT_NOT_FOUND("ERR_PROD_001", HttpStatus.NOT_FOUND, "Sản phẩm không tồn tại"),
    VARIANT_NOT_FOUND("ERR_VAR_001", HttpStatus.NOT_FOUND, "Biến thể sản phẩm không tồn tại"),
    STOCK_INSUFFICIENT("ERR_STOCK_001", HttpStatus.BAD_REQUEST, "Số lượng hàng trong kho không đủ đáp ứng"),
    CART_NOT_FOUND("ERR_CART_001", HttpStatus.NOT_FOUND, "Không tìm thấy giỏ hàng"),
    COUPON_NOT_FOUND("ERR_COUP_001", HttpStatus.NOT_FOUND, "Mã giảm giá không tồn tại"),
    COUPON_EXPIRED("ERR_COUP_002", HttpStatus.BAD_REQUEST, "Mã giảm giá đã hết hạn"),
    COUPON_LIMIT_REACHED("ERR_COUP_003", HttpStatus.BAD_REQUEST, "Bạn đã dùng hết số lần cho phép của mã này"),
    ORDER_NOT_FOUND("ERR_ORDR_001", HttpStatus.NOT_FOUND, "Không tìm thấy đơn hàng"),
    ORDER_STATUS_INVALID("ERR_ORDR_002", HttpStatus.BAD_REQUEST, "Trạng thái đơn hàng không hợp lệ cho thao tác này"),
    CONVERSATION_NOT_FOUND("ERR_CHAT_001", HttpStatus.NOT_FOUND, "Cuộc hội thoại không tồn tại");

    private final String code;
    private final HttpStatus httpStatus;
    private final String defaultMessage;

    ErrorCode(String code, HttpStatus httpStatus, String defaultMessage){
        this.code = code;
        this.httpStatus = httpStatus;
        this.defaultMessage = defaultMessage;
    }
}
