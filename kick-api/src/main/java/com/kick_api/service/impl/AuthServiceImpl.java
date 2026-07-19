package com.kick_api.service.impl;

import com.kick_api.config.JwtTokenProvider;
import com.kick_api.constant.ErrorCode;
import com.kick_api.dto.*;
import com.kick_api.entity.User;
import com.kick_api.exception.AppException;
import com.kick_api.service.AuthService;
import com.kick_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Override
    @Transactional
    public User register(RegisterRequest request) {
        // Xây dựng entity User từ dữ liệu request và gọi UserService xử lý
        // (mã hóa mật khẩu, gán role mặc định CUSTOMER, kiểm tra trùng email/phone)
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .build();
        return userService.registerUser(user);
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        // Bước 1: Xác thực email và mật khẩu thông qua Spring Security + BCrypt
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Bước 2: Nạp thông tin xác thực vào Security Context của phiên hiện tại
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Bước 3: Sinh Access Token JWT ngắn hạn (24h mặc định)
        String accessToken = tokenProvider.generateAccessToken(authentication);

        // Bước 4: Sinh Refresh Token dài hạn (7 ngày mặc định) và lưu vào bảng users
        String refreshToken = tokenProvider.generateRefreshToken();
        Instant refreshExpiry = tokenProvider.calculateRefreshTokenExpiry();

        User user = userService.getUserByEmail(request.getEmail());
        userService.saveRefreshToken(user.getId(), refreshToken, refreshExpiry);

        // Bước 5: Trả về DTO chứa cặp token và thông tin người dùng
        return LoginResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .user(user)
                .build();
    }

    @Override
    @Transactional
    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        String oldRefreshToken = request.getRefreshToken();

        // Bước 1: Tìm user tương ứng với refresh token được gửi lên
        User user = userService.findByRefreshToken(oldRefreshToken);

        // Bước 2: Kiểm tra thời hạn của refresh token
        if (user.getRefreshTokenExpiry() == null || user.getRefreshTokenExpiry().isBefore(Instant.now())) {
            // Refresh token đã hết hạn: xóa khỏi DB và yêu cầu đăng nhập lại
            userService.clearRefreshToken(user.getId());
            throw new AppException(ErrorCode.INVALID_INPUT, "Refresh token đã hết hạn! Vui lòng đăng nhập lại.");
        }

        // Bước 3: Sinh Access Token mới (tạo Authentication tạm với email của user)
        Authentication fakeAuth = new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(), "", Collections.emptyList()
                ),
                null
        );
        String newAccessToken = tokenProvider.generateAccessToken(fakeAuth);

        // Bước 4: Xoay vòng Refresh Token (tạo mới + cập nhật vào DB, tăng bảo mật)
        String newRefreshToken = tokenProvider.generateRefreshToken();
        Instant newRefreshExpiry = tokenProvider.calculateRefreshTokenExpiry();
        userService.saveRefreshToken(user.getId(), newRefreshToken, newRefreshExpiry);

        return TokenRefreshResponse.builder()
                .token(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    @Override
    @Transactional
    public void logout(TokenRefreshRequest request) {
        // Tìm user theo refresh token và xóa token đó khỏi DB để vô hiệu hóa phiên
        User user = userService.findByRefreshToken(request.getRefreshToken());
        userService.clearRefreshToken(user.getId());
    }
}
