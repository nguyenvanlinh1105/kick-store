package com.kick_api.service;

import com.kick_api.entity.User;

public interface UserService {
    
    User registerUser(User user);
    
    User getUserById(Long id);
    
    User getUserByEmail(String email);
    
    User updateUserProfile(Long id, User profileDetails);
    
    void changePassword(Long id, String oldPassword, String newPassword);
}
