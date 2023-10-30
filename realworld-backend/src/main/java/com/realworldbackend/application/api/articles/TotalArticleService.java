package com.realworldbackend.application.api.articles;

import com.realworldbackend.application.security.UserPayload;
import com.realworldbackend.domain.article.Article;
import com.realworldbackend.domain.article.ArticleService;
import com.realworldbackend.domain.article.tag.TagService;
import com.realworldbackend.domain.user.User;
import com.realworldbackend.domain.user.UserNotFoundException;
import com.realworldbackend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class TotalArticleService {

    private final ArticleService articleService;
    private final UserService userService;
    private final TagService tagService;

    @Transactional
    public SingleArticleResponse createArticle(final String title, final String description, final String body, final Set<String> tagList, final Long userId) {
        return userService.findUserById(userId)
                .map(author -> articleService.save(title, description, body, author))
                .map(article -> article.addTags(tagService.saveIfNotExists(tagList)))
                .map(SingleArticleResponse::fromArticle)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public SingleArticleResponse updateArticles(String slug, UserPayload userPayload, Optional<String> title, Optional<String> body, Optional<String> description) {
        articleService.checkSlugDuplicated(slug);
        Article article = articleService.findArticleBySlugAndUserId(slug, userPayload.getUserId())
                .update(title, body, description);
        return SingleArticleResponse.fromArticle(article);
    }

    @Transactional
    public SingleArticleResponse favorite(String slug, UserPayload userPayload) {
        User viewer = userService.getUserById(userPayload.getUserId());
        Article article = articleService.getArticleBySlug(slug).addFavorite(viewer);
        viewer.addFavoritedArticle(article.withFavorited(viewer));
        return SingleArticleResponse.fromArticle(article);
    }

    @Transactional
    public SingleArticleResponse unfavorite(String slug, UserPayload userPayload) {
        Article article = articleService.getArticleBySlug(slug);
        articleService.removeFavorite(article, userService.getUserById(userPayload.getUserId()));
        return SingleArticleResponse.fromArticle(article);
    }


    public MultipleArticleResponse findByCondition(String author, String favorited, String tag, int limit, int offset) {
        final List<Article> articles = articleService.findAll(author, favorited, tag, limit, offset);
        return MultipleArticleResponse.fromArticle(articles);
    }

    public MultipleArticleResponse feed(UserPayload userPayload, Pageable pageable) {
        final User viewer = userService.getUserById(userPayload.getUserId());
        return MultipleArticleResponse.fromArticle(articleService.findArticlesByAuthors(viewer.getFollowers(), pageable));
    }

    public SingleArticleResponse findBySlug(String slug) {
        return SingleArticleResponse.fromArticle(articleService.getArticleBySlug(slug));
    }

    public MultipleArticleResponse findByCondition(UserPayload payload, String author, String favorited, String tag, int limit, int offset) {
        final List<Article> articles = articleService.findAll(author, favorited, tag, limit, offset)
                .stream().map(article -> article.withFavorited(userService.getUserById(payload.getUserId())))
                .toList();

        return MultipleArticleResponse.fromArticle(articles);
    }
}

