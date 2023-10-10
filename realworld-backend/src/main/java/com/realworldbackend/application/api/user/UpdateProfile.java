package com.realworldbackend.application.api.user;

import com.realworldbackend.domain.user.User;
import com.realworldbackend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateProfile {

    private final UserService userService;

    public User update(Long userId, Optional<String> email, Optional<String> username, Optional<String> password, Optional<String> bio, Optional<String> image) {
        return userService.getUserById(userId).update(email, username, password, bio, image);
    }
}
