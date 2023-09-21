package com.realworldbackend.user.api.request;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public record UserUpdateRequest(
        String email,
        String bio,
        String image,
        String password,
        String username
) {

}
