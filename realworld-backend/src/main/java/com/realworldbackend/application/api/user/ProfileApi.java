package com.realworldbackend.application.api.user;

import com.realworldbackend.application.api.users.UserResponse;
import com.realworldbackend.application.security.UserPayload;
import com.realworldbackend.domain.user.User;
import com.realworldbackend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ProfileApi {

    private final UpdateProfile updateProfile;

    private final UserService userService;

    @GetMapping
    ResponseEntity<UserResponse> currentUser(@AuthenticationPrincipal UserPayload payload) {
        return ResponseEntity.status(HttpStatus.OK).body(UserResponse.from(userService.getUserById(payload.getUserId())));
    }


    @PutMapping
    ResponseEntity<UserResponse> updateUser(
            @AuthenticationPrincipal UserPayload payload,
            @RequestBody UpdateProfileRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(UserResponse.from(updateProfile.update(payload.getUserId(),
                request.getEmail(),
                request.getUsername(),
                request.getPassword(),
                request.getBio(),
                request.getImage()
        )));
    }
}
