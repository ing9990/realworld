package com.realworldbackend.user.domain;

import com.realworldbackend.common.exception.ErrorCode;
import com.realworldbackend.common.exception.SystemException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Avatar {

    protected static final String DEFAULT_BIO = "";
    protected static final String DEFAULT_IMAGE = "null";

    @Column(name = "user_avatar_bio")
    private String bio;

    @Column(name = "user_avatar_image")
    private String image;

    protected Avatar() {
        new Avatar(DEFAULT_BIO, DEFAULT_IMAGE);
    }

    protected Avatar(String bio, String image) {
        checkBioAndImageIsNotEmpty(bio, image);

        this.bio = bio;
        this.image = image;
    }

    public void updateProfile(final String bio, final String image) {
        checkBioAndImageIsNotEmpty(bio, image);
        
        this.bio = bio;
        this.image = image;
    }

    private void checkBioAndImageIsNotEmpty(
            final String bio,
            final String image
    ) {
        if (bio.isEmpty() || image.isEmpty()) {
            throw new SystemException("User bio and user image cannot be empty. ", ErrorCode.INVALID_INPUT);
        }
    }
}
