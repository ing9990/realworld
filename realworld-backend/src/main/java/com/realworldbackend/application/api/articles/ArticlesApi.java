package com.realworldbackend.application.api.articles;

import com.realworldbackend.application.security.UserPayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/articles")
@RequiredArgsConstructor
@RestController
public class ArticlesApi {

    private final TotalArticleService totalArticleService;


    @PostMapping
    public ResponseEntity<SingleArticleResponse> createArticles(
            @AuthenticationPrincipal UserPayload payload,
            @Valid @RequestBody CreateArticleRequest createArticleRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(totalArticleService.createArticle(
                createArticleRequest.getTitle(),
                createArticleRequest.getDescription(),
                createArticleRequest.getBody(),
                createArticleRequest.getTagList(),
                payload.getUserId()
        ));
    }

    @GetMapping
    public ResponseEntity<MultipleArticleResponse> findArticles(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String favorited,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "0") int offset
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(totalArticleService.findAll(
                author, favorited, tag, limit, offset
        ));
    }
}
