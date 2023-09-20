package com.realworldbackend.user.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Avatar {

    protected static final String DEFAULT_BIO = "";
    protected static final String DEFAULT_IMAGE = "null";

    private String bio;

    private String image;

    protected Avatar() {
        new Avatar(DEFAULT_BIO, DEFAULT_IMAGE);
    }

    protected Avatar(String bio, String image) {
        this.bio = bio;
        this.image = image;
    }

    public void updateProfile(String bio, String image) {
        this.bio = bio;
        this.image = image;
    }
}
