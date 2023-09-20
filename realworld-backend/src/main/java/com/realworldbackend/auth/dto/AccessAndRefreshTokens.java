package com.realworldbackend.auth.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccessAndRefreshTokens {
    private String refreshToken;

    private String accessToken;
}
