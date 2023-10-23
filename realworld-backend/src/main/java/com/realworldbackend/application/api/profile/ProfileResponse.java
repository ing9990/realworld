package com.realworldbackend.application.api.profile;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.realworldbackend.domain.user.Avatar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
public class ProfileResponse {

    private String username;
    private String bio;
    private String image;
    private boolean following;

    public static ProfileResponse fromAvatar(final Avatar avatar) {
        return new ProfileResponse(avatar.getUsername(), avatar.getBio(), avatar.getImage(), avatar.isFollow());
    }
}
