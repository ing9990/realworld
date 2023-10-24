package com.realworldbackend.application.api.user;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@NoArgsConstructor
@JsonTypeName("user")
@Getter
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
class UpdateProfileRequest {

    private String email;
    private String username;
    private String password;
    private String image;
    private String bio;

    public Optional<String> getEmail() {
        return Optional.ofNullable(this.email);
    }

    public Optional<String> getUsername() {
        return Optional.ofNullable(this.username);
    }

    public Optional<String> getPassword() {
        return Optional.ofNullable(this.password);
    }

    public Optional<String> getImage() {
        return Optional.ofNullable(this.image);
    }

    public Optional<String> getBio() {
        return Optional.ofNullable(this.bio);
    }
}

