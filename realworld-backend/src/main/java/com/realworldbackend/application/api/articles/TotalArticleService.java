package com.realworldbackend.application.api.articles;

import com.realworldbackend.domain.article.ArticleCondition;
import com.realworldbackend.domain.article.ArticleService;
import com.realworldbackend.domain.article.tag.TagService;
import com.realworldbackend.domain.user.User;
import com.realworldbackend.domain.user.UserNotFoundException;
import com.realworldbackend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
class TotalArticleService {

    private final ArticleService articleService;
    private final UserService userService;
    private final TagService tagService;

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

    public MultipleArticleResponse findAll(Pageable pageable) {
        return MultipleArticleResponse.fromArticle(articleService.findAll(pageable));
    }

    public MultipleArticleResponse findAll(ArticleCondition condition) {
        return MultipleArticleResponse.fromArticle(articleService.findAll(condition));
    }

    public MultipleArticleResponse findAll(Pageable pageable, Long viewerId) {
        final User viewer = userService.getUserById(viewerId);

        return MultipleArticleResponse.fromArticle(articleService.findAll(pageable)
                .stream().map(article -> article.withFavorited(viewer))
                .toList());
    }

    public MultipleArticleResponse findAll(String author, String favorited, String tag, int limit, int offset) {
        return MultipleArticleResponse.fromArticle(articleService.findAll(author, favorited, tag, limit, offset));
    }
}
