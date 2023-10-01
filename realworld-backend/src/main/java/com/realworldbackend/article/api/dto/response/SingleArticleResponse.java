package com.realworldbackend.article.api.dto.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.realworldbackend.article.domain.Article;
import com.realworldbackend.article.domain.Tag;
import com.realworldbackend.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@JsonTypeName("article")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@Builder
public record SingleArticleResponse(
        String slug,
        String title,
        String description,
        String body,
        Set<String> tagList,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean favorited,
        int favoritesCount,
        SingleArticleAuthorResponse author
) {
    record SingleArticleAuthorResponse(
            String username,
            String bio,
            String image,
            boolean following
    ) {
        public static SingleArticleAuthorResponse from(
                final User currentUser
        ) {
            return new SingleArticleAuthorResponse(
                    currentUser.getUsername(),
                    currentUser.getAvatar().getBio(),
                    currentUser.getAvatar().getImage(),
                    false
            );
        }
    }

    public static SingleArticleResponse from(
            final Article article,
            final User currentUser
    ) {
        return SingleArticleResponse.builder()
                .title(article.getTitle())
                .slug(article.getSlug())
                .description(article.getDescription())
                .body(article.getBody())
                .tagList(article.getTags().stream().map(Tag::getName).collect(Collectors.toSet()))
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .favorited(article.isFavorited(currentUser))
                .favoritesCount(article.getFavoritesUsers().size())
                .author(SingleArticleAuthorResponse.from(article.getAuthor()))
                .build();
    }
}
