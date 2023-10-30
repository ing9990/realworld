package com.realworldbackend.application.api.articles;

import com.realworldbackend.application.security.UserPayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static java.util.Optional.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RequestMapping("/api/articles")
@RequiredArgsConstructor
@RestController
public class ArticlesApi {

    private final TotalArticleService totalArticleService;

    @PostMapping
    ResponseEntity<SingleArticleResponse> createArticles(
            @AuthenticationPrincipal UserPayload payload,
            @Valid @RequestBody CreateArticleRequest createArticleRequest
    ) {
        return status(HttpStatus.CREATED).body(totalArticleService.createArticle(
                createArticleRequest.getTitle(),
                createArticleRequest.getDescription(),
                createArticleRequest.getBody(),
                createArticleRequest.getTagList(),
                payload.getUserId()
        ));
    }

    @PutMapping("/{slug}")
    ResponseEntity<SingleArticleResponse> updateArticle(
            @AuthenticationPrincipal UserPayload userPayload,
            @PathVariable String slug,
            @RequestBody @Valid UpdateArticleRequest updateArticleRequest
    ) {
        return status(OK).body(totalArticleService.updateArticles(slug, userPayload, updateArticleRequest.getTitle(), updateArticleRequest.getBody(), updateArticleRequest.getDescription()));
    }

    @GetMapping
    ResponseEntity<MultipleArticleResponse> findArticles(
            @AuthenticationPrincipal UserPayload userPayload,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String favorited,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "0") int offset
    ) {
        return status(OK).body(
                ofNullable(userPayload)
                        .map(payload -> totalArticleService.findByCondition(payload, author, favorited, tag, limit, offset))
                        .orElseGet(() -> totalArticleService.findByCondition(author, favorited, tag, limit, offset))
        );
    }

    @GetMapping("/feed")
    public ResponseEntity<MultipleArticleResponse> getFeed(
            @AuthenticationPrincipal UserPayload userPayload,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return status(OK).body(totalArticleService.feed(userPayload, pageable));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<SingleArticleResponse> getArticle(
            @PathVariable String slug
    ) {
        return status(OK).body(totalArticleService.findBySlug(slug));
    }

    @PostMapping("/{slug}/favorite")
    public ResponseEntity<SingleArticleResponse> favortie(
            @AuthenticationPrincipal UserPayload userPayload,
            @PathVariable String slug
    ) {
        return status(OK).body(totalArticleService.favorite(slug, userPayload));
    }

    @DeleteMapping("/{slug}/favorite")
    public ResponseEntity<SingleArticleResponse> unFavortie(
            @AuthenticationPrincipal UserPayload userPayload,
            @PathVariable String slug
    ) {
        return status(OK).body(totalArticleService.unfavorite(slug, userPayload));
    }
}
