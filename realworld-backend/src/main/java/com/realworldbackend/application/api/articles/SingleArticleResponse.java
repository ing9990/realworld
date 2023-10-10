package com.realworldbackend.application.api.articles;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.realworldbackend.application.api.user.ProfileResponse;
import com.realworldbackend.domain.article.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonTypeName("article")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
public class SingleArticleResponse {

    private String title;
    private String slug;
    private String description;
    private String body;
    private Set<String> tagList;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private boolean favorited;
    private Long favoritesCount;
    private ProfileResponse author;


    public static SingleArticleResponse fromArticle(final Article article) {
        return SingleArticleResponse.builder()
                .title(article.getTitle())
                .slug(article.getSlug())
                .description(article.getDescription())
                .body(article.getBody())
                .tagList(article.getTags())
                .createdAt(article.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")))
                .updatedAt(article.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")))
                .favorited(article.isFavorited())
                .favoritesCount((long) article.getUserFavorited().size())
                .author(ProfileResponse.fromAvatar(article.getAuthor().getAvatar()))
                .build();
    }
}
