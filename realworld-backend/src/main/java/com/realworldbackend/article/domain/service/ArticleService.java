package com.realworldbackend.article.domain.service;

import com.realworldbackend.article.api.dto.ArticleFindCondition;
import com.realworldbackend.article.api.dto.response.MultiArticlesResponse;
import com.realworldbackend.article.api.dto.response.SingleArticleResponse;
import com.realworldbackend.article.domain.Article;
import com.realworldbackend.article.domain.Tag;
import com.realworldbackend.article.domain.repo.ArticleRepository;
import com.realworldbackend.article.domain.repo.TagRepository;
import com.realworldbackend.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;

    @Transactional
    public Long makeArticle(
            final User author,
            final String title,
            final String description,
            final String body,
            final List<String> tags
    ) {
        Article article = articleRepository.save(Article.makeArticle(title, description, body, author));

        Set<Tag> existingTags = new HashSet<>(tagRepository.findByTagNameIn(tags));

        Set<Tag> newTags = tags.stream()
                .filter(tagName ->
                        existingTags.stream()
                                .noneMatch(existingTag -> existingTag.getName().equals(tagName)))
                .map(tagName ->
                        existingTags.stream()
                                .filter(existingTag -> existingTag.getName().equals(tagName))
                                .findFirst()
                                .orElseGet(() -> new Tag(tagName))
                ).collect(Collectors.toSet());

        List<Tag> savedTags = tagRepository.saveAllAndFlush(newTags);

        existingTags.addAll(savedTags);
        article.addTags(existingTags);

        return article.getId();
    }

    @Transactional(readOnly = true)
    public SingleArticleResponse getArticle(final Long articleId) {
        Article article = articleRepository.findArticleById(articleId);

        return SingleArticleResponse.from(article);
    }

    @Transactional(readOnly = true)
    public MultiArticlesResponse searchCondition(final ArticleFindCondition articleFindCondition) {
        log.info(articleFindCondition.toString());

        List<Article> articles = articleRepository.search(articleFindCondition);

        log.info(articles.toString());

        return MultiArticlesResponse.from(articles);
    }
}
