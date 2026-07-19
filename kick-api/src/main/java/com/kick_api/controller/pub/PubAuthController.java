package com.kick_api.controller.pub;

import com.kick_api.dto.ApiResponse;
import com.kick_api.dto.RegisterRequest;
import com.kick_api.entity.User;
import com.kick_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pub/auth")
@RequiredArgsConstructor
public class PubAuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@Valid @RequestBody RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .build();

        User registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(
                ApiResponse.success("Đăng ký tài khoản thành công!", registeredUser),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> login(@RequestParam String email, @RequestParam String password) {
        User user = userService.getUserByEmail(email);
        // Kiểm tra mật khẩu (đơn giản cho mục đích demo/chạy thử)
        if (!user.getPassword().equals(password)) {
            throw new com.kick_api.exception.AppException(
                    com.kick_api.constant.ErrorCode.INVALID_INPUT, 
                    "Mật khẩu đăng nhập không chính xác!"
            );
        }
        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công!", user));
    }
}
