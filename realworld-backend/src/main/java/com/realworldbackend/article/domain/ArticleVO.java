package com.realworldbackend.article.domain;

import com.realworldbackend.user.domain.User;

import java.time.LocalDateTime;
import java.util.Set;

public record ArticleVO(
        String id,
        String title,
        String slug,
        String description,
        String body,
        User author,
        Set<String> tagList,
        Set<User> favoritesUsers,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
