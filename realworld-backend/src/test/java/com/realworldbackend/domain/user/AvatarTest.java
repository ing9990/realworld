package com.realworldbackend.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class AvatarTest {
    Avatar avatar;

    @BeforeEach
    void each() {
        avatar = User.registration("jake", "jake@jake.com", "jakejake").getAvatar();
    }


    @DisplayName("유저가 요청한 값만 수정할 경우 요청하지 않은 정보는 변경되지 않고 수정 요청한 정보만 변경된다.")
    @Test
    void updateProfile() {
        // given when
        avatar.updateProfile(ofNullable("updated_jake"), ofNullable("jake@jake.jake"), Optional.empty());

        // then
        assertThat(avatar).isNotNull();
        assertThat(avatar.getUsername()).isEqualTo("updated_jake");
        assertThat(avatar.getImage()).isEmpty();
        assertThat(avatar.getBio()).isEqualTo("jake@jake.jake");
    }
}