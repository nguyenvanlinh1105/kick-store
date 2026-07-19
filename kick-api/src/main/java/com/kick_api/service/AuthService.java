package com.kick_api.service;

import com.kick_api.dto.*;
import com.kick_api.entity.User;

public interface AuthService {

    // Đăng ký tài khoản mới
    User register(RegisterRequest request);

    // Đăng nhập và trả về Access Token + Refresh Token
    LoginResponse login(LoginRequest request);

    // Cấp lại Access Token mới bằng Refresh Token còn hiệu lực
    TokenRefreshResponse refreshToken(TokenRefreshRequest request);

    // Đăng xuất và vô hiệu hóa Refresh Token hiện tại
    void logout(TokenRefreshRequest request);
}
