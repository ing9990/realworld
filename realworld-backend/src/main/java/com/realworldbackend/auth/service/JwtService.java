package com.realworldbackend.auth.service;

import com.realworldbackend.auth.domain.RefreshToken;
import com.realworldbackend.auth.domain.RefreshTokenRepository;
import com.realworldbackend.auth.dto.AccessAndRefreshTokens;
import com.realworldbackend.common.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.realworldbackend.common.exception.ErrorCode.INVALID_REFRESH_TOKEN;

@Service
@Transactional
@RequiredArgsConstructor
public class JwtService {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public AccessAndRefreshTokens makeAndGetToken(Long userId) {
        AccessAndRefreshTokens tokens = jwtProvider.generateLoginToken(String.valueOf(userId));
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findRefreshTokenByUserId(userId);

        if (refreshToken.isPresent()) {
            refreshTokenRepository.deleteTokenByuserId(userId);
        }
        refreshTokenRepository.save(new RefreshToken(tokens.getRefreshToken(), userId));
        return tokens;
    }

    public String renewalAccessToken(final String refreshTokenRequest, final String authorizationHeader) {
        final String accessToken = authorizationHeader.substring(7);

        if (jwtProvider.isValidRefreshAndInvalidAccess(refreshTokenRequest, accessToken)) {
            final RefreshToken refreshToken = refreshTokenRepository.findRefreshTokenByToken(refreshTokenRequest)
                    .orElseThrow(() -> new AuthException(INVALID_REFRESH_TOKEN));

            return jwtProvider.regenerateAccessToken(refreshToken.getUserId().toString());
        }
        throw new AuthException(ErrorCode.FAIL_TO_VALIDATE_TOKEN);
    }
}
