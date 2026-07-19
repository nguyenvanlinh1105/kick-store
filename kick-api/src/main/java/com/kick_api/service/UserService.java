package com.kick_api.service;

import com.kick_api.entity.User;

import java.time.Instant;

public interface UserService {
    
    User registerUser(User user);
    
    User getUserById(Long id);
    
    User getUserByEmail(String email);
    
    User updateUserProfile(Long id, User profileDetails);
    
    void changePassword(Long id, String oldPassword, String newPassword);

    // Lưu refresh token và thời gian hết hạn của nó vào bảng users
    void saveRefreshToken(Long userId, String refreshToken, Instant expiryDate);

    // Tìm user dựa trên refresh token (để xác thực khi yêu cầu cấp lại Access Token)
    User findByRefreshToken(String refreshToken);

    // Xóa refresh token khi người dùng đăng xuất
    void clearRefreshToken(Long userId);
}

