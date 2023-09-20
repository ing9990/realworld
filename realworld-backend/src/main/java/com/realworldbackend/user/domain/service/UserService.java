package com.realworldbackend.user.domain.service;

import com.realworldbackend.common.exception.ErrorCode;
import com.realworldbackend.user.domain.User;
import com.realworldbackend.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public void registration(final String username, final String email, final String password) {
        validateUsernmaeDuplicate(username);
        validateEmailDuplicate(email);

        userRepository.save(User.registration(username, email, encoder.encode(password)));
    }

    public User getUserByEmail(final String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER));
    }

    private void validateUsernmaeDuplicate(final String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameDuplicatedException(ErrorCode.DUPLICATED_USERNAME);
        }
    }

    private void validateEmailDuplicate(final String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UsernameDuplicatedException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    public User getUserByEmailAndPassword(String email, String password) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.EMAIL_MISMATCH));

        if (encoder.matches(password, user.getPassword())) {
            return user;
        }

        throw new PasswordMisMatchException(ErrorCode.PASSWORD_MISMATCH);
    }
}
