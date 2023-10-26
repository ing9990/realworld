package com.realworldbackend.application.api.articles;

import com.realworldbackend.application.api.user.profile.ProfileResponse;
import com.realworldbackend.domain.article.Article;
import lombok.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
class MultipleArticleResponse {

    List<ArticleResponse> articles;
    int articlesCount;

    public static MultipleArticleResponse from(List<ArticleResponse> articles) {
        return new MultipleArticleResponse(articles, articles.size());
    }

    public static MultipleArticleResponse fromArticle(List<Article> articles) {

        return new MultipleArticleResponse(articles.stream().map(ArticleResponse::fromArticle)
                .toList(), articles.size());
    }

    @Getter
    @AllArgsConstructor
    @Builder
    static class ArticleResponse {

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


        public static ArticleResponse fromArticle(final Article article) {
            return ArticleResponse.builder()
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
}
