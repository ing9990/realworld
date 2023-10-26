package com.realworldbackend.application.api.user;

import com.realworldbackend.application.exception.BusinessException;
import com.realworldbackend.application.exception.ErrorCode;
import com.realworldbackend.application.security.UserPayload;
import com.realworldbackend.domain.auth.AccessAndRefreshTokens;
import com.realworldbackend.domain.auth.JwtService;
import com.realworldbackend.domain.user.User;
import com.realworldbackend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class TotalUserService {

    private final PasswordEncoder encoder;
    private final UserService userService;
    private final JwtService jwtService;

    @Transactional
    public UserResponse registration(
            final String email,
            final String username,
            final String rawPassword
    ) {
        checkUsernameDuplicated(username);
        checkEmailDuplicated(email);

        final User user = userService.save(email, username, encoder.encode(rawPassword));
        final AccessAndRefreshTokens tokens = jwtService.makeAndGetToken(user.getId());

        return UserResponse.from(user, tokens.getAccessToken());
    }

    @Transactional
    public UserResponse login(String email, String password) {
        final User user = userService.findUserByEmail(email)
                .filter(foundUser -> encoder.matches(password, foundUser.getPassword()))
                .orElseThrow(() -> new BusinessException(ErrorCode.LOGIN_FAIL));
        return UserResponse.from(user, jwtService.makeAndGetToken(user.getId()).getAccessToken());
    }

    @Transactional
    public UserResponse update(UserPayload payload, Optional<String> email, Optional<String> username, Optional<String> password, Optional<String> bio, Optional<String> image) {
        return UserResponse.from(userService.getUserById(payload.getUserId()).update(email, username, password, bio, image));
    }

    public UserResponse me(final UserPayload payload) {
        return UserResponse.from(userService.getUserById(payload.getUserId()));
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
