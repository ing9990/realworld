package com.realworldbackend.user.domain.service;

import com.realworldbackend.common.exception.ErrorCode;
import com.realworldbackend.user.domain.User;
import com.realworldbackend.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void registration(final String username, final String email, final String password) {
        validateUsernmaeDuplicate(username);
        validateEmailDuplicate(email);

        userRepository.save(User.registration(username, email, password));
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
}
