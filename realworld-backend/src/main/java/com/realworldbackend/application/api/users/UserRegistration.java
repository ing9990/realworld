package com.realworldbackend.application.api.users;

import com.realworldbackend.application.exception.BusinessException;
import com.realworldbackend.application.exception.ErrorCode;
import com.realworldbackend.domain.user.User;
import com.realworldbackend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserRegistration {

    private final UserService userService;
    private final PasswordEncoder encoder;

    public User registration(
            final String email,
            final String username,
            final String rawPassword
    ) {
        checkUsernameDuplicated(username);
        checkEmailDuplicated(email);

        return userService.save(email, username, encoder.encode(rawPassword));
    }

    private void checkEmailDuplicated(String email) {
        if (userService.existsUserByEmail(email)) {
            throw new BusinessException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    private void checkUsernameDuplicated(String username) {
        if (userService.existsUserByUsername(username)) {
            throw new BusinessException(ErrorCode.DUPLICATED_EMAIL);
        }
    }
}
