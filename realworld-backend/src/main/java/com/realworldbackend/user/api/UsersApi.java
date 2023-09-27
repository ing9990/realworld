package com.realworldbackend.user.api;

import com.realworldbackend.auth.dto.AccessAndRefreshTokens;
import com.realworldbackend.auth.service.JwtService;
import com.realworldbackend.user.api.request.UserLoginRequest;
import com.realworldbackend.user.api.request.UserRegistrationRequest;
import com.realworldbackend.user.api.response.UserResponse;
import com.realworldbackend.user.domain.User;
import com.realworldbackend.user.domain.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UsersApi {

    public static final int COOKIE_AGE_SECONDS = 604800;

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<UserResponse> registration(
            @Valid @RequestBody UserRegistrationRequest request,
            HttpServletResponse httpServletResponse
    ) {
        userService.registration(request.username(), request.email(), request.password());

        final User user = userService.getUserByEmail(request.email());
        final AccessAndRefreshTokens accessAndRefreshTokens = jwtService.makeAndGetToken(user.getId());
        final ResponseCookie cookie = ResponseCookie.from("refresh-token", accessAndRefreshTokens.getRefreshToken())
                .maxAge(COOKIE_AGE_SECONDS)
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();
        httpServletResponse.addHeader(SET_COOKIE, cookie.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(user, accessAndRefreshTokens.getAccessToken()));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(
            @Valid @RequestBody UserLoginRequest request,
            HttpServletResponse httpServletResponse
    ) {
        User user = userService.getUserByEmailAndPassword(request.email(), request.password());

        AccessAndRefreshTokens accessAndRefreshTokens = jwtService.makeAndGetToken(user.getId());

        final ResponseCookie cookie = ResponseCookie.from("refresh-token", accessAndRefreshTokens.getRefreshToken())
                .maxAge(COOKIE_AGE_SECONDS)
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();
        httpServletResponse.addHeader(SET_COOKIE, cookie.toString());

        return ResponseEntity.status(HttpStatus.OK).body(UserResponse.from(user, accessAndRefreshTokens.getAccessToken()));
    }
}