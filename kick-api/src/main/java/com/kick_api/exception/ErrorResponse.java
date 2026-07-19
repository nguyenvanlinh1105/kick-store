package com.kick_api.exception;

import lombok.*;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private Instant timestamp;
    private int status;
    private String code;
    private String message;
}
