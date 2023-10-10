package com.realworldbackend.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Getter;

import java.util.Optional;

@Embeddable
@Getter
public class Avatar {

    protected static final String DEFAULT_BIO = "";
    protected static final String DEFAULT_IMAGE = "";

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "user_avatar_bio")
    private String bio;

    @Column(name = "user_avatar_image")
    private String image;

    @Transient
    private boolean following = false;

    protected Avatar(final String username) {
        this.username = username;
        this.bio = DEFAULT_BIO;
        this.image = DEFAULT_IMAGE;
    }

    protected Avatar() {

    }

    public static Avatar withUsername(String username) {
        return new Avatar(username);
    }

    protected Avatar withFollowing(boolean following) {
        this.following = following;
        return this;
    }

    public void updateProfile(final Optional<String> username, final Optional<String> bio, final Optional<String> image) {
        username.ifPresent(this::changeUsername);
        bio.ifPresent(this::changeBio);
        image.ifPresent(this::changeImage);
    }

    private void changeImage(String image) {
        this.image = image;
    }

    private void changeBio(String bio) {
        this.bio = bio;
    }

    private void changeUsername(String username) {
        this.username = username;
    }

    public boolean isFollow() {
        return this.following;
    }
}
