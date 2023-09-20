package com.realworldbackend.jwt.service;

import com.realworldbackend.jwt.domain.RefreshToken;
import com.realworldbackend.jwt.domain.RefreshTokenRepository;
import com.realworldbackend.jwt.dto.AccessAndRefreshTokens;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class JwtService {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public AccessAndRefreshTokens makeAndGetToken(Long userId) {
        AccessAndRefreshTokens tokens = jwtProvider.generateLoginToken(String.valueOf(userId));

        refreshTokenRepository.save(new RefreshToken(tokens.getRefreshToken(), userId));

        return tokens;
    }
}
