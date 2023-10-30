package com.realworldbackend.application.api.articles.comment;

import com.realworldbackend.application.security.UserPayload;
import com.realworldbackend.domain.article.Article;
import com.realworldbackend.domain.article.ArticleService;
import com.realworldbackend.domain.article.comment.CommentService;
import com.realworldbackend.domain.user.UserNotFoundException;
import com.realworldbackend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

import static org.springframework.data.util.Optionals.mapIfAllPresent;

@Service
@Transactional
@RequiredArgsConstructor
class TotalCommentService {

    private final CommentService commentService;
    private final UserService userService;
    private final ArticleService articleService;

    CommentResponse createComment(UserPayload userPayload, String slug, CommentRequest commentRequest) {
        return CommentResponse.from(mapIfAllPresent(userService.findUserById(userPayload.getUserId()), articleService.findArticleBySlug(slug),
                (user, article) -> commentService.createComment(user, article, commentRequest.getBody()))
                .orElseThrow(NoSuchElementException::new));
    }

    public MultiCommentResponse findAll(UserPayload payload, String slug) {
        final Article article = articleService.getArticleBySlug(slug);

        return userService.findUserById(payload.getUserId())
                .map(article::withFavorited)
                .map(Article::getComments)
                .map(MultiCommentResponse::from)
                .orElseThrow(NoSuchElementException::new);
    }

    public MultiCommentResponse findAll(String slug) {
        return MultiCommentResponse.from(articleService.getArticleBySlug(slug).getComments());
    }
}
