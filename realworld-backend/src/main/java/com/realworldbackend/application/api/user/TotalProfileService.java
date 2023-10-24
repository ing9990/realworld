package com.realworldbackend.application.api.user;

import com.realworldbackend.application.exception.ErrorCode;
import com.realworldbackend.domain.user.User;
import com.realworldbackend.domain.user.UserNotFoundException;
import com.realworldbackend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TotalProfileService {

    private final UserService userService;

    public ProfileResponse find(String celebUsername, Long viewerId) {
        final User viewer = userService.getUserById(viewerId);

        return userService.findUserByUsername(celebUsername)
                .map(celeb -> celeb.viewProfile(viewer))
                .map(ProfileResponse::fromAvatar)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_CELEB));
    }

    public ProfileResponse follow(String username, Long followeeId) {
        final User followee = userService.getUserById(followeeId);

        log.info(followee.getAvatar().getUsername() + " following to " + username);

        return userService.findUserByUsername(username)
                .map(user -> userService.follow(user, followee))
                .map(user -> user.viewProfile(followee))
                .map(ProfileResponse::fromAvatar)
                .orElseThrow(UserNotFoundException::new);
    }

    public ProfileResponse unFollow(String username, Long followeeId) {
        final User followee = userService.getUserById(followeeId);

        log.info(followee.getAvatar().getUsername() + " unfollowing to " + username);

        return userService.findUserByUsername(username)
                .map(user -> userService.unFollow(user, followee))
                .map(user -> user.viewProfile(followee))
                .map(ProfileResponse::fromAvatar)
                .orElseThrow(UserNotFoundException::new);
    }
}
