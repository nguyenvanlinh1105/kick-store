package com.kick_api.dto;

import com.kick_api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    
    @Builder.Default
    private String tokenType = "Bearer";
    
    private User user;
}
