package com.realworldbackend.common.resolvers;

import com.google.common.net.HttpHeaders;
import com.realworldbackend.auth.service.AuthException;
import com.realworldbackend.auth.service.JwtProvider;
import com.realworldbackend.common.annotations.CurrentUser;
import com.realworldbackend.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        try {
            String authorizationToken = webRequest.getHeader(HttpHeaders.AUTHORIZATION);

            if (authorizationToken == null) {
                if (Objects.requireNonNull(parameter
                                .getParameterAnnotation(CurrentUser.class))
                        .required()) {
                    throw new AuthException(ErrorCode.INVALID_ACCESS_TOKEN);
                } else {
                    return new CurrentUserDto(-1L, LoginType.LOGIN_NONE);
                }
            }

            if (!authorizationToken.startsWith("Token ")) {
                throw new AuthException(ErrorCode.INVALID_ACCESS_TOKEN);
            }

            String rawToken = authorizationToken.substring(6);
            String userId = jwtProvider.getSubject(rawToken).trim();

            return new CurrentUserDto(Long.parseLong(userId), LoginType.LOGIN);
        } catch (IllegalArgumentException e) {
            throw new AuthException(ErrorCode.INVALID_ACCESS_TOKEN);
        }
    }
}