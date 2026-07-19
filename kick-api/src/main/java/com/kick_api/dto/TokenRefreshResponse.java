package com.kick_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TokenRefreshResponse {
    private String token;
    private String refreshToken;

    @Builder.Default
    private String tokenType = "Bearer";
}
