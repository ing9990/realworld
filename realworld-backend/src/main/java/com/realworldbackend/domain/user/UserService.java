package com.realworldbackend.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long userId) {
        return userRepository.findUserByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Avatar getAvatarByUserId(Long userId) {
        return userRepository.findUserByUserId(userId).orElseThrow(UserNotFoundException::new).getAvatar();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findUserByUserId(userId).orElseThrow(UserNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existsUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    public User updateUser(Long userId, Optional<String> email, Optional<String> username, Optional<String> password, Optional<String> bio, Optional<String> image) {
        return getUserById(userId).update(email, username,
                Optional.ofNullable(encoder.encode(password.orElseGet(String::new))),
                bio, image);
    }

    public User save(final String email, final String username, final String rawPassword) {
        return userRepository.save(User.registration(username, email, rawPassword));
    }
}
