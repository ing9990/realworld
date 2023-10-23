package com.realworldbackend.application.api.profile;

import com.realworldbackend.application.security.UserPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApi {

    private final TotalUserService totalUserService;

    @GetMapping
    ResponseEntity<UserResponse> currentUser(@AuthenticationPrincipal UserPayload payload) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(totalUserService.me(payload));
    }


    @PutMapping
    ResponseEntity<UserResponse> updateUser(
            @AuthenticationPrincipal UserPayload payload,
            @RequestBody UpdateProfileRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                totalUserService.update(payload,
                        request.getEmail(),
                        request.getUsername(),
                        request.getPassword(),
                        request.getBio(),
                        request.getImage()
                ));
    }
}
