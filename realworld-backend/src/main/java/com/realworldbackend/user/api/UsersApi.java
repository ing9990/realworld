package com.realworldbackend.user.api;

import com.realworldbackend.user.api.request.UserRegistrationRequest;
import com.realworldbackend.user.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersApi {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> registration(
            @Valid @RequestBody UserRegistrationRequest request
    ) {
        userService.registration(request.username(), request.email(), request.password());

        return null;
    }
}