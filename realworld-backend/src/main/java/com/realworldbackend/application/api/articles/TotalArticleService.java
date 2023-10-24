package com.realworldbackend.application.api.articles;

import com.realworldbackend.application.security.UserPayload;
import com.realworldbackend.domain.article.Article;
import com.realworldbackend.domain.article.ArticleCondition;
import com.realworldbackend.domain.article.ArticleService;
import com.realworldbackend.domain.article.tag.TagService;
import com.realworldbackend.domain.user.User;
import com.realworldbackend.domain.user.UserNotFoundException;
import com.realworldbackend.domain.user.UserRepository;
import com.realworldbackend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.realworldbackend.application.api.articles.MultipleArticleResponse.fromArticle;

@Service
@RequiredArgsConstructor
@Transactional
class TotalArticleService {

    private final ArticleService articleService;
    private final UserService userService;
    private final TagService tagService;

    @Transactional
    public SingleArticleResponse createArticle(
            final String title,
            final String description,
            final String body,
            final Set<String> tagList,
            final Long userId
    ) {
        return userService.findUserById(userId)
                .map(author -> articleService.save(title, description, body, author))
                .map(article -> article.addTags(tagService.saveIfNotExists(tagList)))
                .map(SingleArticleResponse::fromArticle)
                .orElseThrow(UserNotFoundException::new);
    }

    public MultipleArticleResponse findAll(String author, String favorited, String tag, int limit, int offset) {
        return fromArticle(articleService.findAll(author, favorited, tag, limit, offset));
    }

    public MultipleArticleResponse feed(UserPayload userPayload, Pageable pageable) {
        final User viewer = userService.getUserById(userPayload.getUserId());

        return MultipleArticleResponse.fromArticle(articleService.findArticlesByAuthors(viewer.getFollowers(), pageable));
    }

    public SingleArticleResponse findBySlug(String slug) {
        return SingleArticleResponse.fromArticle(articleService.findArticleBySlug(slug));
    }

    @Transactional
    public SingleArticleResponse updateArticles(String slug, UserPayload userPayload, Optional<String> title, Optional<String> body, Optional<String> description) {
        Article article = articleService.findArticleBySlugAndUserId(slug, userPayload.getUserId()).update(title, body, description);
        return SingleArticleResponse.fromArticle(article);
    }
}
