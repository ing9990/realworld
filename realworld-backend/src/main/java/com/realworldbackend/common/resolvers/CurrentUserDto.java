package com.realworldbackend.common.resolvers;

import static com.realworldbackend.common.resolvers.LoginType.LOGIN;

public record CurrentUserDto(
        Long userId,
        LoginType loginType
) {
    public boolean isLogin() {
        return this.loginType == LOGIN;
    }
}
