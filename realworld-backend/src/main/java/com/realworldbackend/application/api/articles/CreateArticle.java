package com.realworldbackend.application.api.articles;

import com.realworldbackend.domain.article.ArticleService;
import com.realworldbackend.domain.user.User;
import com.realworldbackend.domain.user.UserNotFoundException;
import com.realworldbackend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateArticle {

    private final ArticleService articleService;
    private final UserService userService;

    public SingleArticleResponse createArticle(
            final String title,
            final String description,
            final String body,
            final Long userId
    ) {
        return userService.findUserById(userId)
                .map(author -> articleService.save(title, description, body, author))
                .map(SingleArticleResponse::fromArticle)
                .orElseThrow(UserNotFoundException::new);
    }
}
