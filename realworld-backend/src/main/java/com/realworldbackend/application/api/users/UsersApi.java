package com.realworldbackend.application.api.users;

import com.realworldbackend.domain.auth.AccessAndRefreshTokens;
import com.realworldbackend.domain.auth.JwtService;
import com.realworldbackend.domain.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersApi {

    private final UserRegistration userRegistration;
    private final UserLogin userLogin;
    private final JwtService jwtService;

    @PostMapping
    ResponseEntity<UserResponse> registration(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        final User user = userRegistration.registration(userRegistrationRequest.getEmail(), userRegistrationRequest.getUsername(), userRegistrationRequest.getPassword());
        final AccessAndRefreshTokens tokens = jwtService.makeAndGetToken(user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(user, tokens.getAccessToken()));
    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        final User user = userLogin.login(userLoginRequest.getEmail(), userLoginRequest.getPassword());
        final AccessAndRefreshTokens tokens = jwtService.makeAndGetToken(user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(user, tokens.getAccessToken()));
    }
}
