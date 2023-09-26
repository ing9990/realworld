package com.realworldbackend.article.api.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.realworldbackend.article.domain.Article;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@JsonTypeName("user")
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
        AuthorResponse author
) {
    public static SingleArticleResponse from(Article article) {
        return SingleArticleResponse.builder()
                .title(article.getTitle())
                .slug(article.getSlug())
                .description(article.getDescription())
                .body(article.getBody())
                .tagList(article.getTagList())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .favorited(false)
                .favoritesCount(article.getFavoritesUser().size())
                .author(AuthorResponse.from(article.getAuthor()))
                .build();
    }
}
