package com.realworldbackend.article.domain.repo;


import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends ArticleJpaRepository, ArticleQueryRepository {

}
