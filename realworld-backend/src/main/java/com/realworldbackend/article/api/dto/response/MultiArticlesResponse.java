package com.realworldbackend.article.api.dto.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.realworldbackend.article.domain.Article;
import com.realworldbackend.article.domain.Tag;
import com.realworldbackend.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record MultiArticlesResponse(
        List<SingleArticleResponse> articles,
        Integer articlesCount
) {
    public static MultiArticlesResponse from(
            final List<Article> articles,
            final User currentUser
    ) {
        List<SingleArticleResponse> articleResponse = articles
                .stream()
                .map(article -> SingleArticleResponse.from(article, currentUser))
                .toList();

        return new MultiArticlesResponse(articleResponse, articleResponse.size());
    }

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
            MultiArticlesResponse.SingleArticleResponse.SingleArticleAuthorResponse author
    ) {
        record SingleArticleAuthorResponse(
                String username,
                String bio,
                String image,
                boolean following
        ) {
            public static MultiArticlesResponse.SingleArticleResponse.SingleArticleAuthorResponse from(
                    final User currentUser
            ) {
                return new MultiArticlesResponse.SingleArticleResponse.SingleArticleAuthorResponse(
                        currentUser.getUsername(),
                        currentUser.getAvatar().getBio(),
                        currentUser.getAvatar().getImage(),
                        false
                );
            }
        }

        public static MultiArticlesResponse.SingleArticleResponse from(
                final Article article,
                final User currentUser
        ) {
            return MultiArticlesResponse.SingleArticleResponse.builder()
                    .title(article.getTitle())
                    .slug(article.getSlug())
                    .description(article.getDescription())
                    .body(article.getBody())
                    .tagList(article.getTags().stream().map(Tag::getName).collect(Collectors.toSet()))
                    .createdAt(article.getCreatedAt())
                    .updatedAt(article.getUpdatedAt())
                    .favorited(article.isFavorited(currentUser))
                    .favoritesCount(article.getFavoritesUsers().size())
                    .author(MultiArticlesResponse.SingleArticleResponse.SingleArticleAuthorResponse.from(article.getAuthor()))
                    .build();
        }
    }
}
