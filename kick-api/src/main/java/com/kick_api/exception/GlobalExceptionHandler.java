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
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {
    // 1. Xử lý toàn bộ lỗi nghiệp vụ AppException
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(AppException ex){
        ErrorCode errorcode = ex.getErrorCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.NOT_FOUND.value())
                .code(errorcode.getCode())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, errorcode.getHttpStatus());
    }
    // 2. Xử lý lỗi validate dữ liệu
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Objects>> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error",ErrorCode.INVALID_INPUT.getCode());
        body.put("details", errors);
        return null;
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
