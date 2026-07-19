package com.kick_api.controller.common;

import com.kick_api.dto.*;
import com.kick_api.entity.User;
import com.kick_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pub/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * [POST] /api/v1/pub/auth/register
     * Đăng ký tài khoản người dùng mới
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@Valid @RequestBody RegisterRequest request) {
        User registeredUser = authService.register(request);
        return new ResponseEntity<>(
                ApiResponse.success("Đăng ký tài khoản thành công!", registeredUser),
                HttpStatus.CREATED
        );
    }

    /**
     * [POST] /api/v1/pub/auth/login
     * Đăng nhập và nhận về cặp Access Token + Refresh Token
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công!", response));
    }

    /**
     * [POST] /api/v1/pub/auth/refresh
     * Cấp lại Access Token mới bằng Refresh Token còn hiệu lực
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenRefreshResponse>> refresh(@Valid @RequestBody TokenRefreshRequest request) {
        TokenRefreshResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(ApiResponse.success("Cấp lại token thành công!", response));
    }

    /**
     * [POST] /api/v1/pub/auth/logout
     * Đăng xuất và vô hiệu hóa Refresh Token hiện tại
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@Valid @RequestBody TokenRefreshRequest request) {
        authService.logout(request);
        return ResponseEntity.ok(ApiResponse.success("Đăng xuất thành công!", null));
    }
}
