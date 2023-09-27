package com.realworldbackend.article.api;

import com.realworldbackend.article.api.request.CreateArticleRequest;
import com.realworldbackend.article.api.response.SingleArticleResponse;
import com.realworldbackend.article.domain.Article;
import com.realworldbackend.article.domain.service.ArticleService;
import com.realworldbackend.common.annotations.CurrentUser;
import com.realworldbackend.common.resolvers.CurrentUserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleApi {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<SingleArticleResponse> createArticle(
            @CurrentUser CurrentUserDto currentUserDto,
            @Valid @RequestBody CreateArticleRequest createArticleRequest
    ) {
        Long articleId = articleService.makeArticle(
                currentUserDto.userId(),
                createArticleRequest.title(),
                createArticleRequest.description(),
                createArticleRequest.body(),
                createArticleRequest.tagList()
        );

        SingleArticleResponse article = articleService.getArticle(articleId);

        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }
}
