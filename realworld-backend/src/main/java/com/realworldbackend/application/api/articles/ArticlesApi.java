package com.realworldbackend.application.api.articles;

import com.realworldbackend.application.security.UserPayload;
import com.realworldbackend.domain.article.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/articles")
@RequiredArgsConstructor
@RestController
public class ArticlesApi {

    private final CreateArticle createArticle;

    @PostMapping
    public ResponseEntity<SingleArticleResponse> createArticles(
            @AuthenticationPrincipal UserPayload payload,
            @Valid @RequestBody CreateArticleRequest createArticleRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createArticle.createArticle(
                createArticleRequest.getTitle(),
                createArticleRequest.getDescription(),
                createArticleRequest.getBody(),
                payload.getUserId()
        ));
    }

}
