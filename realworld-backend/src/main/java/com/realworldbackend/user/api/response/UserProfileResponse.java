package com.realworldbackend.user.api.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.realworldbackend.user.domain.User;

@JsonTypeName("profile")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public record UserProfileResponse(
        String username,
        String bio,
        String image,
        boolean following
) {
    public UserProfileResponse(String username, String bio, String image, boolean following) {
        this.username = username;
        this.bio = bio;
        this.image = image;
        this.following = following;
    }

    public static UserProfileResponse from(
            final User targetUser,
            final User currentUser
    ) {
        return new UserProfileResponse(targetUser.getUsername(),
                targetUser.getAvatar().getBio(),
                targetUser.getAvatar().getImage(),
                targetUser.getFollowers().contains(currentUser)
        );
    }
}
