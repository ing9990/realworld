package com.realworldbackend.article.api.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.realworldbackend.article.domain.Article;
import com.realworldbackend.user.domain.User;
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
        SingleArticleAuthorResponse author
) {
    record SingleArticleAuthorResponse(
            String username,
            String bio,
            String image,
            boolean following
    ) {

        public static SingleArticleAuthorResponse from(User currentUser, User user) {
            return new SingleArticleAuthorResponse(
                    currentUser.getUsername(),
                    currentUser.getAvatar().getBio(),
                    currentUser.getAvatar().getImage(),
                    currentUser.getFollowers().isFollow(user)
            );
        }
    }

    public static SingleArticleResponse from(Article article, User currentUser) {
        return SingleArticleResponse.builder()
                .title(article.getTitle())
                .slug(article.getSlug())
                .description(article.getDescription())
                .body(article.getBody())
                .tagList(article.getTagList())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .favorited(article.isFavorited(currentUser))
                .favoritesCount(article.getFavoritesUser().size())
                .author(SingleArticleAuthorResponse.from(article.getAuthor(), currentUser))
                .build();
    }
}
