package com.realworldbackend.article.api;

import com.realworldbackend.article.api.dto.ArticleFindCondition;
import com.realworldbackend.article.api.dto.request.CreateArticleRequest;
import com.realworldbackend.article.api.dto.response.MultiArticlesResponse;
import com.realworldbackend.article.api.dto.response.SingleArticleResponse;
import com.realworldbackend.article.domain.service.ArticleService;
import com.realworldbackend.common.annotations.CurrentUser;
import com.realworldbackend.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleApi {

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<MultiArticlesResponse> getAllArticles(
            @CurrentUser(required = false) User currentUser,
            ArticleFindCondition articleFindCondition
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                articleService.searchCondition(currentUser, articleFindCondition)
        );
    }

    @PostMapping
    public ResponseEntity<SingleArticleResponse> createArticle(
            @CurrentUser(required = false) User currentUser,
            @Valid @RequestBody CreateArticleRequest createArticleRequest
    ) {
        Long articleId = articleService.makeArticle(
                currentUser,
                createArticleRequest.title(),
                createArticleRequest.description(),
                createArticleRequest.body(),
                createArticleRequest.tagList()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                articleService.getArticle(currentUser, articleId)
        );
    }
}
