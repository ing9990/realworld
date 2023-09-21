package com.realworldbackend.user.domain.service;

import com.realworldbackend.common.exception.ErrorCode;
import com.realworldbackend.common.resolvers.CurrentUserDto;
import com.realworldbackend.user.api.response.UserProfileResponse;
import com.realworldbackend.user.domain.Follow;
import com.realworldbackend.user.domain.FollowRepository;
import com.realworldbackend.user.domain.User;
import com.realworldbackend.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;


@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public UserProfileResponse getProfileByUsername(
            final CurrentUserDto currentUser,
            final String username
    ) {
        User targetUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER_FOLLOW));

        System.out.println(targetUser);

        if (currentUser.isLogin()) {
            User user = userRepository.findUserByUserId(currentUser.userId())
                    .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER_FOLLOW));

            boolean follow = followRepository.existsByUserAndFollowing(user, targetUser);
            return UserProfileResponse.from(targetUser, follow);
        }

        return UserProfileResponse.from(targetUser);
    }

    public UserProfileResponse follow(CurrentUserDto currentUser, String username) {
        User user = userRepository.findUserByUserId(currentUser.userId()).orElseThrow(
                () -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER_FOLLOW));

        User target = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER_FOLLOW));

        if (followRepository.existsByUserAndFollowing(user, target)) {
            return UserProfileResponse.from(target, true);
        }
        followRepository.save(new Follow(user, target));
        return UserProfileResponse.from(target, true);
    }

    public UserProfileResponse unfollow(CurrentUserDto currentUser, String username) {
        User user = userRepository.findUserByUserId(currentUser.userId()).orElseThrow(
                () -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER_FOLLOW));

        User target = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER_FOLLOW));

        followRepository.deleteByUserAndTargetUser(user, target);
        return UserProfileResponse.from(target, false);
    }
}
