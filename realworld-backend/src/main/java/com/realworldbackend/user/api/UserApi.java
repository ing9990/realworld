package com.realworldbackend.user.api;

import com.realworldbackend.auth.dto.AccessAndRefreshTokens;
import com.realworldbackend.auth.service.JwtService;
import com.realworldbackend.common.annotations.CurrentUser;
import com.realworldbackend.user.api.request.UserUpdateRequest;
import com.realworldbackend.user.api.response.UserResponse;
import com.realworldbackend.user.domain.User;
import com.realworldbackend.user.domain.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.OutputKeys;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<?> currentUser(
            @CurrentUser Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(UserResponse.from(userService.getUserByUserId(userId), ""));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(
            @CurrentUser Long userId,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        userService.updateUser(userId, request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(UserResponse.from(userService.getUserByUserId(userId), ""));

    }
}
