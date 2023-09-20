package com.realworldbackend.user.api.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.realworldbackend.user.domain.User;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public record UserResponse(String email, String token, String username, String bio, String image) {
    public static UserResponse from(final User user, final String token) {
        return new UserResponse(
                user.getEmail(), token,
                user.getUsername(),
                user.getAvatar().getBio(),
                user.getAvatar().getImage()
        );
    }

    public UserResponse(String email, String token, String username, String bio, String image) {
        this.email = email;
        this.token = token;
        this.username = username;
        this.bio = bio;
        this.image = image;
    }
}
