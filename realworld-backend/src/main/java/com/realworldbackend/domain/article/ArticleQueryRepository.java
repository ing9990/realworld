package com.realworldbackend.domain.article;

import java.util.List;

public interface ArticleQueryRepository {
    List<Article> findAllByCondition(String tagName, String favoritesName, String authorName, int offset, int limit);
}
