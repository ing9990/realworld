package com.realworldbackend.application.api.user;

import com.realworldbackend.application.security.UserPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
class UserApi {

    private final TotalUserService totalUserService;

    @GetMapping
    ResponseEntity<UserResponse> currentUser(@AuthenticationPrincipal UserPayload payload) {
        return status(HttpStatus.OK).body(totalUserService.me(payload));
    }

    @PutMapping
    ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal UserPayload payload, @RequestBody UpdateProfileRequest request) {
        return status(HttpStatus.OK).body(totalUserService.update(payload, request.getEmail(), request.getUsername(), request.getPassword(), request.getBio(), request.getImage()));
    }
}
