package com.realworldbackend.domain.article;

import com.realworldbackend.application.exception.BusinessException;
import com.realworldbackend.application.exception.ErrorCode;
import com.realworldbackend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article save(
            final String title,
            final String description,
            final String body,
            final User author
    ) {
        checkSlugDuplicated(toSlug(title));
        return articleRepository.save(Article.makeArticle(title, description, body, author));
    }

    private String toSlug(String title) {
        StringBuilder slugBuilder = new StringBuilder();
        Arrays.stream(title.split(" "))
                .forEach(word ->
                        slugBuilder.append(word).append("-"));
        return slugBuilder.substring(0, slugBuilder.length() - 1);
    }

    public List<Article> findAll(String author, String favorited, String tag, int limit, int offset) {
        return articleRepository.findAllByCondition(tag, favorited, author, offset, limit);
    }

    public List<Article> findArticlesByAuthors(Set<User> followers, Pageable pageable) {
        return articleRepository.findArticlesByAuthorIsIn(followers, pageable);
    }

    public Article getArticleBySlug(String slug) {
        return articleRepository.findArticleBySlug(slug)
                .orElseThrow(ArticleNotFoundException::new);
    }

    public Optional<Article> findArticleBySlug(String slug) {
        return articleRepository.findArticleBySlug(slug);
    }

    public Article findArticleBySlugAndUserId(String slug, Long userId) {
        return articleRepository.findArticleBySlugAndAuthorId(slug, userId)
                .orElseThrow(ArticleNotFoundException::new);
    }

    public void removeFavorite(Article article, User viewer) {
        article.removeFavoritedUser(viewer);
        viewer.removeFavoritedAritcle(article);
    }

    public void checkSlugDuplicated(final String slug) {
        if (articleRepository.existsBySlug(slug)) {
            throw new BusinessException(ErrorCode.DUPLICATED_SLUG);
        }
    }
}
