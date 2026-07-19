package com.kick_api.controller.pub;

import com.kick_api.config.JwtTokenProvider;
import com.kick_api.constant.ErrorCode;
import com.kick_api.dto.*;
import com.kick_api.entity.User;
import com.kick_api.exception.AppException;
import com.kick_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/pub/auth")
@RequiredArgsConstructor
public class PubAuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    /**
     * [POST] /api/v1/pub/auth/register
     * Đăng ký tài khoản người dùng mới
     */
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

    /**
     * [POST] /api/v1/pub/auth/login
     * Đăng nhập và nhận về cặp Access Token + Refresh Token
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Xác thực email và mật khẩu qua Spring Security + BCrypt
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // Nạp thông tin xác thực vào Security Context của phiên hiện tại
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Sinh Access Token JWT ngắn hạn (24h)
        String accessToken = tokenProvider.generateAccessToken(authentication);

        // Sinh Refresh Token dài hạn (7 ngày) và lưu thẳng vào bảng users
        String newRefreshToken = tokenProvider.generateRefreshToken();
        Instant refreshTokenExpiry = tokenProvider.calculateRefreshTokenExpiry();
        User user = userService.getUserByEmail(loginRequest.getEmail());
        userService.saveRefreshToken(user.getId(), newRefreshToken, refreshTokenExpiry);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(accessToken)
                .refreshToken(newRefreshToken)
                .user(user)
                .build();

        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công!", loginResponse));
    }

    /**
     * [POST] /api/v1/pub/auth/refresh
     * Cấp lại Access Token mới sử dụng Refresh Token hợp lệ
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenRefreshResponse>> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        // Tìm user theo refresh token được gửi lên
        User user = userService.findByRefreshToken(requestRefreshToken);

        // Kiểm tra xem refresh token đã hết hạn chưa
        if (user.getRefreshTokenExpiry() == null || user.getRefreshTokenExpiry().isBefore(Instant.now())) {
            // Refresh token hết hạn: xóa token cũ và yêu cầu đăng nhập lại
            userService.clearRefreshToken(user.getId());
            throw new AppException(ErrorCode.INVALID_INPUT, "Refresh token đã hết hạn! Vui lòng đăng nhập lại.");
        }

        // Tạo Access Token mới dựa trên thông tin email của user
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(), "", java.util.Collections.emptyList()
                ),
                null
        );

        // Sinh Access Token mới
        String newAccessToken = tokenProvider.generateAccessToken(authentication);

        // Xoay vòng Refresh Token mới (bảo mật tốt hơn, tránh reuse attack)
        String newRefreshToken = tokenProvider.generateRefreshToken();
        Instant newRefreshExpiry = tokenProvider.calculateRefreshTokenExpiry();
        userService.saveRefreshToken(user.getId(), newRefreshToken, newRefreshExpiry);

        TokenRefreshResponse response = TokenRefreshResponse.builder()
                .token(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();

        return ResponseEntity.ok(ApiResponse.success("Cấp lại token thành công!", response));
    }

    /**
     * [POST] /api/v1/pub/auth/logout
     * Đăng xuất và vô hiệu hóa refresh token hiện tại
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        User user = userService.findByRefreshToken(requestRefreshToken);
        // Xóa refresh token khỏi CSDL để vô hiệu hóa phiên
        userService.clearRefreshToken(user.getId());
        return ResponseEntity.ok(ApiResponse.success("Đăng xuất thành công!", null));
    }
}
