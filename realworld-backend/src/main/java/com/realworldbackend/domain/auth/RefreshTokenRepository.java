package com.realworldbackend.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("select r from RefreshToken r where r.userId = ?1")
    Optional<RefreshToken> findRefreshTokenByUserId(Long userId);

    @Query("select r from RefreshToken r where r.token = ?1")
    Optional<RefreshToken> findRefreshTokenByToken(String token);

    @Modifying
    @Query("delete from RefreshToken r where r.userId = ?1")
    void deleteTokenByuserId(Long userId);
}
