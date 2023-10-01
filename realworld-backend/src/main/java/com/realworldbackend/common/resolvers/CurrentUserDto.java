package com.realworldbackend.common.resolvers;

import com.realworldbackend.user.domain.User;

import static com.realworldbackend.common.resolvers.LoginType.LOGIN;

public record CurrentUserDto(
        User currentUser,
        LoginType loginType
) {
    public boolean isLogin() {
        return this.loginType == LOGIN;
    }
}
