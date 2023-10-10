package com.realworldbackend.domain.auth;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccessAndRefreshTokens {
    private String refreshToken;

    private String accessToken;
}
