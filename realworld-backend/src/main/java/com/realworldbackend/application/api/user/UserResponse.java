package com.realworldbackend.application.api.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.realworldbackend.domain.user.User;

import lombok.*;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
class UserResponse {

    private String email;
    private String username;
    private String bio;
    private String image;
    private String token;

    public static UserResponse from(User user, String token) {
        return new UserResponse(user.getEmail(), user.getAvatar().getUsername(), user.getAvatar().getBio(), user.getAvatar().getImage(), token);
    }

    public static UserResponse from(User user) {
        return new UserResponse(user.getEmail(), user.getAvatar().getUsername(), user.getAvatar().getBio(), user.getAvatar().getImage(), "");
    }
}