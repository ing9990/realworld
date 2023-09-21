package com.realworldbackend.user.domain;

import com.realworldbackend.common.exception.ErrorCode;
import com.realworldbackend.common.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Embeddable
@Getter
public class Avatar {

    protected static final String DEFAULT_BIO = "";
    protected static final String DEFAULT_IMAGE = "";

    @Column(name = "user_avatar_bio")
    private String bio;

    @Column(name = "user_avatar_image")
    private String image;

    protected Avatar() {
        this.bio = DEFAULT_BIO;
        this.image = DEFAULT_IMAGE;
    }

    public void updateProfile(final String bio, final String image) {
        this.bio = bio == null ? "" : bio;
        this.image = image == null ? "" : image;
    }

    private void checkBioAndImageIsNotEmpty(
            final String bio,
            final String image
    ) {
        if (Objects.isNull(bio) || Objects.isNull(image)) {
            throw new BusinessException("User bio and user image cannot be empty. ", ErrorCode.INVALID_INPUT);
        }
    }

    public void updateImage(String image) {
        this.image = image;
    }

    public void updateBio(String bio) {
        this.bio = bio;
    }
}
