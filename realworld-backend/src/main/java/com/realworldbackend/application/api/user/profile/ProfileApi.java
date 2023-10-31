package com.realworldbackend.application.api.user.profile;

import com.realworldbackend.application.security.UserPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
class ProfileApi {

    private final TotalProfileService totalProfileService;

    @GetMapping("/{username}")
    ResponseEntity<ProfileResponse> findProfile(
            @AuthenticationPrincipal UserPayload payload,
            @PathVariable String username
    ) {
        return status(OK)
                .body(totalProfileService.find(username, payload.getUserId()));
    }

    @PostMapping("/{username}/follow")
    ResponseEntity<ProfileResponse> follow(
            @AuthenticationPrincipal UserPayload payload,
            @PathVariable String username
    ) {
        return status(OK).body(
                totalProfileService.follow(username, payload.getUserId())
        );
    }

    @DeleteMapping("/{username}/follow")
    ResponseEntity<ProfileResponse> unfollow(
            @AuthenticationPrincipal UserPayload payload,
            @PathVariable String username
    ) {
        return status(OK).body(
                totalProfileService.unFollow(username, payload.getUserId())
        );
    }
}
