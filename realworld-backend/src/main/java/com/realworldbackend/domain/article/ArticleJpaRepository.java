package com.realworldbackend.domain.article;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleJpaRepository extends JpaRepository<Article, Long> {

    @EntityGraph(
            value = "with_tagList",
            type = EntityGraph.EntityGraphType.FETCH
    )
    Article findArticleById(Long articleId);
}
