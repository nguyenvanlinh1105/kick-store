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

import com.kick_api.dto.LoginRequest;
import com.kick_api.dto.LoginResponse;
import com.kick_api.config.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/v1/pub/auth")
@RequiredArgsConstructor
public class PubAuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

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
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Thực hiện xác thực người dùng bằng email và mật khẩu
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // Nạp thông tin xác thực vào Security Context của Spring
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Sinh chuỗi JWT Token
        String jwt = tokenProvider.generateToken(authentication);

        // Đọc thông tin chi tiết user từ DB để trả về cho Frontend hiển thị profile
        User user = userService.getUserByEmail(loginRequest.getEmail());

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwt)
                .user(user)
                .build();

        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công!", loginResponse));
    }
}
