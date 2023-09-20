package com.realworldbackend.user.api.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

public record UserRegistrationRequest(

        @NotEmpty(message = "Username cannot be empty.")
        String username,

        @NotEmpty(message = "Email cannot be empty.")
        String email,

        @NotEmpty(message = "Password cannot be empty.")
        String password
) {
}
