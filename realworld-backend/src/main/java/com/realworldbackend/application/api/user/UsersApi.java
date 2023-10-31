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
class UsersApi {

    private final TotalUserService totalUserService;

    @PostMapping
    ResponseEntity<UserResponse> registration(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        return status(CREATED).body(totalUserService.registration(userRegistrationRequest.getEmail(), userRegistrationRequest.getUsername(), userRegistrationRequest.getPassword()));
    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        return status(CREATED).body(totalUserService.login(userLoginRequest.getEmail(), userLoginRequest.getPassword()));
    }
}
