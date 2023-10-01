package com.realworldbackend.user.domain.service;

import com.realworldbackend.common.exception.ErrorCode;
import com.realworldbackend.common.resolvers.CurrentUserDto;
import com.realworldbackend.user.api.response.UserProfileResponse;
import com.realworldbackend.user.domain.User;
import com.realworldbackend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserProfileResponse getProfileByUsername(
            final User currentUser,
            final String username
    ) {
        User targetUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER_FOLLOW));

        return UserProfileResponse.from(targetUser, currentUser);
    }

    @Transactional
    public UserProfileResponse follow(User currentUser, String username) {
        User target = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER_FOLLOW));

        target.followTargetUser(currentUser);
        return UserProfileResponse.from(target, currentUser);
    }

    @Transactional
    public UserProfileResponse unfollow(User currentUser, String username) {
        User target = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER_FOLLOW));

        target.unFollowTargetUser(currentUser);

        return UserProfileResponse.from(target, currentUser);
    }
}
