package com.realworldbackend.application.api.users;

import com.realworldbackend.application.exception.BusinessException;
import com.realworldbackend.application.exception.ErrorCode;
import com.realworldbackend.domain.user.User;
import com.realworldbackend.domain.user.UserNotFoundException;
import com.realworldbackend.domain.user.UserRepository;
import com.realworldbackend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserLogin {

    private final PasswordEncoder encoder;
    private final UserService userService;

    public User login(String email, String password) {
        return userService.findUserByEmail(email)
                .filter(user -> encoder.matches(password, user.getPassword()))
                .orElseThrow(() -> new BusinessException(ErrorCode.LOGIN_FAIL));
    }
}
