package com.realworldbackend.article.api.dto.response;

import com.realworldbackend.article.domain.Article;

import java.util.List;

public record MultiArticlesResponse(
        List<SingleArticleResponse> articles,
        Integer articlesCount
) {
    public static MultiArticlesResponse from(
            final List<Article> articles
    ) {
        List<SingleArticleResponse> articleResponse = articles.stream().map(SingleArticleResponse::from).toList();

        return new MultiArticlesResponse(articleResponse, articleResponse.size());
    }
}
