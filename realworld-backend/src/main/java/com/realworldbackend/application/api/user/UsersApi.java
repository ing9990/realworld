package com.realworldbackend.application.api.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersApi {

    private final TotalUserService totalUserService;

    @PostMapping
    ResponseEntity<UserResponse> registration(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        final UserResponse user = totalUserService.registration(userRegistrationRequest.getEmail(), userRegistrationRequest.getUsername(), userRegistrationRequest.getPassword());
        return status(CREATED).body(user);
    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        final UserResponse user = totalUserService.login(userLoginRequest.getEmail(), userLoginRequest.getPassword());
        return status(CREATED).body(user);
    }
}
