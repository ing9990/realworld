package com.realworldbackend.user.api;

import com.realworldbackend.common.annotations.CurrentUser;
import com.realworldbackend.common.resolvers.CurrentUserDto;
import com.realworldbackend.user.api.response.UserProfileResponse;
import com.realworldbackend.user.domain.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles/{username}")
@RequiredArgsConstructor
public class ProfilesApi {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<UserProfileResponse> getProfile(
            @CurrentUser(required = false) CurrentUserDto currentUser,
            @PathVariable String username
    ) {
        UserProfileResponse response = profileService.getProfileByUsername(currentUser, username);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/follow")
    public ResponseEntity<UserProfileResponse> followProfile(
            @CurrentUser CurrentUserDto currentUser,
            @PathVariable String username
    ) {
        UserProfileResponse response = profileService.follow(currentUser, username);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/follow")
    public ResponseEntity<UserProfileResponse> unfollowProfile(
            @CurrentUser CurrentUserDto currentUser,
            @PathVariable String username
    ) {
        UserProfileResponse response = profileService.unfollow(currentUser, username);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
