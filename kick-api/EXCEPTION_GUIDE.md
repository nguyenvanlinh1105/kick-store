# Hướng dẫn Xử lý Ngoại lệ Tập trung (ErrorCode & AppException) - KickVerse

Tài liệu này hướng dẫn cách xây dựng cấu trúc **Xử lý Ngoại lệ Tự định nghĩa (Custom Exception)** sử dụng mẫu thiết kế kết hợp **Enum ErrorCode** và lớp **AppException** duy nhất trong dự án **KickVerse (kick-api)** sử dụng **Spring Boot 3**.

Phương pháp này giúp bạn quản lý tất cả các mã lỗi và câu thông báo lỗi tập trung tại một nơi (file Enum), tránh việc phải tạo ra quá nhiều class Exception khác nhau.

---

## 1. Định nghĩa danh sách Mã lỗi (Enum ErrorCode)

Tạo enum này trong package **`com.kick_api.constant`** để định nghĩa tất cả các lỗi có thể xảy ra trong hệ thống, bao gồm cả HTTP Status tương ứng và thông báo lỗi mặc định.

#### ErrorCode.java
```java
package com.kick_api.constant;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // 1. Nhóm lỗi Hệ thống & Validation dữ liệu đầu vào
    INVALID_INPUT("ERR_VAL_001", HttpStatus.BAD_REQUEST, "Dữ liệu đầu vào không hợp lệ"),
    INTERNAL_ERROR("ERR_SYS_999", HttpStatus.INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi hệ thống nghiêm trọng"),

    // 2. Nhóm lỗi Người dùng & Địa chỉ
    USER_NOT_FOUND("ERR_USER_001", HttpStatus.NOT_FOUND, "Người dùng không tồn tại"),
    ADDRESS_NOT_FOUND("ERR_ADDR_001", HttpStatus.NOT_FOUND, "Địa chỉ không tồn tại"),

    // 3. Nhóm lỗi Danh mục & Sản phẩm (Catalog)
    PRODUCT_NOT_FOUND("ERR_PROD_001", HttpStatus.NOT_FOUND, "Sản phẩm không tồn tại"),
    VARIANT_NOT_FOUND("ERR_VAR_001", HttpStatus.NOT_FOUND, "Biến thể sản phẩm không tồn tại"),
    STOCK_INSUFFICIENT("ERR_STOCK_001", HttpStatus.BAD_REQUEST, "Số lượng hàng trong kho không đủ đáp ứng"),

    // 4. Nhóm lỗi Khuyến mãi, Giỏ hàng & Đơn hàng
    CART_NOT_FOUND("ERR_CART_001", HttpStatus.NOT_FOUND, "Không tìm thấy giỏ hàng"),
    COUPON_NOT_FOUND("ERR_COUP_001", HttpStatus.NOT_FOUND, "Mã giảm giá không tồn tại"),
    COUPON_EXPIRED("ERR_COUP_002", HttpStatus.BAD_REQUEST, "Mã giảm giá đã hết hạn"),
    COUPON_LIMIT_REACHED("ERR_COUP_003", HttpStatus.BAD_REQUEST, "Bạn đã dùng hết số lần cho phép của mã này"),
    ORDER_NOT_FOUND("ERR_ORDR_001", HttpStatus.NOT_FOUND, "Không tìm thấy đơn hàng"),
    ORDER_STATUS_INVALID("ERR_ORDR_002", HttpStatus.BAD_REQUEST, "Trạng thái đơn hàng không hợp lệ cho thao tác này");

    private final String code;          // Mã lỗi trả về client (Ví dụ: "ERR_STOCK_001")
    private final HttpStatus httpStatus; // HTTP Status tương ứng (Ví dụ: 400 Bad Request)
    private final String defaultMessage;// Câu thông báo lỗi mặc định

    ErrorCode(String code, HttpStatus httpStatus, String defaultMessage) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.defaultMessage = defaultMessage;
    }

    public String getCode() { return code; }
    public HttpStatus getHttpStatus() { return httpStatus; }
    public String getDefaultMessage() { return defaultMessage; }
}
```

---

## 2. Tạo Class Exception duy nhất (AppException)

Tạo class này trong package **`com.kick_api.exception`**. Class này sẽ mang theo `ErrorCode` khi được ném ra.

#### AppException.java
```java
package com.kick_api.exception;

import com.kick_api.constant.ErrorCode;

public class AppException extends RuntimeException {
    
    private final ErrorCode errorCode;

    // Sử dụng câu thông báo mặc định của ErrorCode
    public AppException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
    }

    // Cho phép ghi đè câu thông báo lỗi chi tiết hơn khi cần thiết
    public AppException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
```

---

## 3. Cấu trúc JSON lỗi phản hồi (ErrorResponsen   DTO)

Tạo class này trong package **`com.kick_api.exception`** để làm khuôn mẫu JSON lỗi trả về cho client. Nó chứa trường `code` để Frontend bắt được mã lỗi chi tiết.

#### ErrorResponse.java
```java
package com.kick_api.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private Instant timestamp;
    private int status;        // HTTP Status code (ví dụ: 400)
    private String code;       // Business Error Code (ví dụ: "ERR_STOCK_001")
    private String message;    // Câu thông báo lỗi chi tiết
}
```

---

## 4. Xây dựng Bộ xử lý lỗi tập trung (Global Exception Handler)

Tạo class này trong package **`com.kick_api.exception`** để tự động bắt tất cả các ngoại lệ và định dạng JSON lỗi chuẩn hóa.

#### GlobalExceptionHandler.java
```java
package com.kick_api.exception;

import com.kick_api.constant.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Xử lý toàn bộ lỗi nghiệp vụ AppException
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(errorCode.getHttpStatus().value())
                .code(errorCode.getCode()) // Lấy mã lỗi từ Enum (Ví dụ: "ERR_STOCK_001")
                .message(ex.getMessage())
                .build();
                
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    // 2. Xử lý lỗi validate dữ liệu đầu vào (DTO validation như @NotBlank, @Min, @Size...)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", ErrorCode.INVALID_INPUT.getCode()); // "ERR_VAL_001"
        body.put("details", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // 3. Xử lý tất cả các ngoại lệ hệ thống khác chưa được cấu hình cụ thể (HTTP 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code(ErrorCode.INTERNAL_ERROR.getCode()) // "ERR_SYS_999"
                .message("Hệ thống gặp lỗi nghiêm trọng! Chi tiết: " + ex.getMessage())
                .build();
                
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

---

## 5. Hướng dẫn sử dụng trong tầng Service

Khi triển khai code ở tầng Service, bạn không cần quan tâm đến cách render JSON lỗi hay HTTP Status code. Chỉ cần `throw` đối tượng `AppException` kèm mã lỗi thích hợp:

```java
// Ví dụ 1: Tìm kiếm không thấy bản ghi (Lấy câu thông báo mặc định)
User user = userRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

// Ví dụ 2: Lỗi nghiệp vụ kho hàng
if (variant.getAvailableStock() < quantity) {
    throw new AppException(ErrorCode.STOCK_INSUFFICIENT);
}

// Ví dụ 3: Truyền thêm câu thông báo chi tiết/tùy chỉnh cho sản phẩm
Product product = productRepository.findBySlug(slug)
        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND, "Không tìm thấy sản phẩm có đường dẫn: " + slug));
```
*Global Exception Handler sẽ tự động bắt lấy các ngoại lệ trên và sinh phản hồi dạng JSON lỗi có mã lỗi và mã trạng thái chuẩn chỉnh.*
