package com.realworldbackend.domain.article;


import com.realworldbackend.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends ArticleJpaRepository, ArticleQueryRepository {

    @Query("select a from Article a where a.author in ?1")
    List<Article> findArticlesByAuthorIsIn(Collection<User> user, Pageable pageable);

    @Query("select a from Article a where a.slug = ?1")
    Article getArticleBySlug(String slug);


    @Query("select a from Article a where a.slug = ?1")
    Optional<Article> findArticleBySlug(String slug);


    @Query("select a from Article a where a.slug = ?1 and a.author.id = ?2")
    Optional<Article> findArticleBySlugAndAuthorId(String slug, Long userId);

    @Query("select (count(a) > 0) from Article a where a.slug = ?1")
    boolean existsBySlug(String slug);
}
