package com.realworldbackend.jwt.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Query("select r from RefreshToken r where r.userId = ?1")
    Optional<RefreshToken> findRefreshTokenByUserId(Long userId);

    @Query("select (count(r) > 0) from RefreshToken r where r.userId = ?1")
    boolean existsRefreshTokenByUserId(Long userId);
}
