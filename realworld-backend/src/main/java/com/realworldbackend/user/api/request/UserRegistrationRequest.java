package com.realworldbackend.user.api.request;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotEmpty;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public record UserRegistrationRequest(

        @NotEmpty(message = "Username cannot be empty.")
        String username,

        @NotEmpty(message = "Email cannot be empty.")
        String email,

        @NotEmpty(message = "Password cannot be empty.")
        String password
) {
}
