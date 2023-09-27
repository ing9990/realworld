package com.realworldbackend.user.api;

import com.realworldbackend.auth.service.JwtService;
import com.realworldbackend.common.annotations.CurrentUser;
import com.realworldbackend.common.resolvers.CurrentUserDto;
import com.realworldbackend.user.api.request.UserUpdateRequest;
import com.realworldbackend.user.api.response.UserResponse;
import com.realworldbackend.user.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<?> currentUser(
            @CurrentUser CurrentUserDto currentUserDto
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(UserResponse.from(userService.getUserByUserId(currentUserDto.userId()),
                        jwtService.getRefreshTokenByUserId(currentUserDto.userId())));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(
            @CurrentUser CurrentUserDto currentUserDto,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        userService.updateUser(currentUserDto.userId(), request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(UserResponse.from(userService.getUserByUserId(currentUserDto.userId()),
                        jwtService.getRefreshTokenByUserId(currentUserDto.userId())
                ));
    }
}
