package com.kick_api.service.impl;

import com.kick_api.entity.User;
import com.kick_api.entity.Role;
import com.kick_api.constant.ErrorCode;
import com.kick_api.constant.UserStatus;
import com.kick_api.exception.AppException;
import com.kick_api.repository.UserRepository;
import com.kick_api.repository.RoleRepository;
import com.kick_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Email đã tồn tại trên hệ thống!");
        }
        if (userRepository.existsByPhone(user.getPhone())) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Số điện thoại đã tồn tại trên hệ thống!");
        }

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByName("CUSTOMER")
                    .orElseThrow(() -> new AppException(ErrorCode.INTERNAL_ERROR, "Vai trò mặc định CUSTOMER chưa được tạo trong DB!"));
            java.util.Set<Role> roles = new java.util.HashSet<>();
            roles.add(defaultRole);
            user.setRoles(roles);
        }

        user.setStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "Không tìm thấy người dùng với email: " + email));
    }

    @Override
    @Transactional
    public User updateUserProfile(Long id, User profileDetails) {
        User user = getUserById(id);

        // Kiểm tra thay đổi SĐT có trùng với người khác không
        if (profileDetails.getPhone() != null && !profileDetails.getPhone().equals(user.getPhone())) {
            if (userRepository.existsByPhone(profileDetails.getPhone())) {
                throw new AppException(ErrorCode.INVALID_INPUT, "Số điện thoại mới đã được sử dụng bởi tài khoản khác!");
            }
            user.setPhone(profileDetails.getPhone());
        }

        user.setFullName(profileDetails.getFullName());
        if (profileDetails.getAvatarUrl() != null) {
            user.setAvatarUrl(profileDetails.getAvatarUrl());
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void changePassword(Long id, String oldPassword, String newPassword) {
        User user = getUserById(id);
        
        // So sánh mật khẩu cũ (tạm thời dạng clear-text vì chưa tích hợp spring-security)
        if (!user.getPassword().equals(oldPassword)) {
            throw new AppException(ErrorCode.INVALID_INPUT, "Mật khẩu cũ không chính xác!");
        }

        user.setPassword(newPassword);
        userRepository.save(user);
    }
}
