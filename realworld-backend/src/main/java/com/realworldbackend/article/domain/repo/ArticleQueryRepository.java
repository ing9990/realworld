package com.realworldbackend.article.domain.repo;

import com.realworldbackend.article.api.dto.ArticleFindCondition;
import com.realworldbackend.article.domain.Article;

import java.util.List;

public interface ArticleQueryRepository {
    List<Article> findAll(ArticleFindCondition condition);
}
