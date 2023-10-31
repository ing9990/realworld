package com.realworldbackend.domain.user;

import com.realworldbackend.infrastructure.QueryDslFactoryConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@Import({QueryDslFactoryConfig.class, BCryptPasswordEncoder.class})
@ActiveProfiles("test")
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    void each() {
        userRepository.flush();
    }

    User getUser() {
        return User.registration("jake", "jake@jake.com", encoder.encode("jakejake"));
    }

    User getUserWith(String username, String email, String rawPassword) {
        return User.registration(username, email, encoder.encode(rawPassword));
    }

    @DisplayName("등록된 유저는 Username으로 검색할 수 있다.")
    @Test
    void existsByUsername() {
        // given
        userRepository.save(User.registration("jake", "jake@jake.com", encoder.encode("jakejake")));

        // when
        var user = userRepository.existsByUsername("jake");

        // then
        assertThat(user).isTrue();
    }

    @DisplayName("등록되지 않은 유저는 Username으로 검색되지 않는다.")
    @Test
    void existsByUsername2() {
        // given

        // when
        var user = userRepository.existsByUsername("jake");

        // then
        assertThat(user).isFalse();
    }

    @DisplayName("등록된 유저는 이메일로 조회할 수 있다.")
    @Test
    void existsByEmail() {
        // given
        userRepository.save(User.registration("jake", "jake@jake.com", encoder.encode("jakejake")));

        // when
        var user = userRepository.existsByEmail("jake@jake.com");

        // then
        assertThat(user).isTrue();
    }

    @DisplayName("유저의 이메일을 사용해서 해당 이메일을 가진 유저를 검색할 수 있다.")
    @Test
    void findUserByEmail() {
        // given
        userRepository.save(User.registration("jake", "jake@jake.com", encoder.encode("jakejake")));

        // when
        var user = userRepository.findUserByEmail("jake@jake.com");

        // then
        assertThat(user).isPresent();
        assertThat(user.get()).isNotNull();
        assertThat(user.get().getEmail()).isEqualTo("jake@jake.com");
    }

    @DisplayName("유저의 아이디로 해당 아이디를 가진 유저를 검색할 수 있다.")
    @Test
    void findUserByUserId() {
        // given
        userRepository.save(User.registration("jake", "jake@jake.com", encoder.encode("jakejake")));

        // when
        var user = userRepository.findUserByUserId(1L);

        // then
        Assertions.assertThat(user).isPresent();
        Assertions.assertThat(user.get().getId()).isEqualTo(1L);
        Assertions.assertThat(user.get().getEmail()).isEqualTo("jake@jake.com");
    }

    @DisplayName("유저의 이름으로 해당 이름을 가진 유저를 검색할 수 있다.")
    @Test
    void findUserByUsername() {
        // given
        userRepository.save(User.registration("jake", "jake@jake.com", encoder.encode("jakejake")));

        // when
        var user = userRepository.findUserByUsername("jake");

        // then
        Assertions.assertThat(user).isPresent();
        Assertions.assertThat(user.get()).isNotNull();
        Assertions.assertThat(user.get().getAvatar().getUsername()).isEqualTo("jake");
    }
}