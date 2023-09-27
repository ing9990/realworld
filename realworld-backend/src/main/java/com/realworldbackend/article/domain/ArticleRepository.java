package com.realworldbackend.article.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleRepository extends JpaRepository<Article, Long> {

    @EntityGraph(
            value = "with_tagList",
            type = EntityGraph.EntityGraphType.FETCH
    )
    Article findArticleById(Long articleId);
}
