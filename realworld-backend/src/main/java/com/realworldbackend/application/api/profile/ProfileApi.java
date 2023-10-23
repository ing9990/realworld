package com.realworldbackend.application.api.profile;

import com.realworldbackend.application.security.UserPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileApi {

    private final TotalProfileService totalProfileService;

    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponse> findProfile(
            @AuthenticationPrincipal UserPayload payload,
            @PathVariable String username
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(totalProfileService.find(username, payload.getUserId()));
    }

    @PostMapping("/{username}/follow")
    public ResponseEntity<ProfileResponse> follow(
            @AuthenticationPrincipal UserPayload payload,
            @PathVariable String username
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                totalProfileService.follow(username, payload.getUserId())
        );
    }

    @DeleteMapping("/{username}/follow")
    public ResponseEntity<ProfileResponse> unfollow(
            @AuthenticationPrincipal UserPayload payload,
            @PathVariable String username
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                totalProfileService.unFollow(username, payload.getUserId())
        );
    }
}
