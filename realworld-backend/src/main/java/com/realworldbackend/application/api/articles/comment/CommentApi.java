package com.realworldbackend.application.api.articles.comment;

import com.realworldbackend.application.security.UserPayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static java.util.Optional.ofNullable;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/articles/{slug}/comments")
@RequiredArgsConstructor
public class CommentApi {

    private final TotalCommentService totalCommentService;

    @PostMapping
    ResponseEntity<CommentResponse> createComment(@AuthenticationPrincipal UserPayload userPayload, @PathVariable String slug, @Valid @RequestBody CommentRequest commentRequest) {
        return status(HttpStatus.CREATED).body(totalCommentService.createComment(userPayload, slug, commentRequest));
    }

    @GetMapping
    ResponseEntity<MultiCommentResponse> findAll(@AuthenticationPrincipal UserPayload userPayload, @PathVariable String slug) {
        return status(HttpStatus.OK).body(ofNullable(userPayload)
                .map(userId -> totalCommentService.findAll(userId, slug))
                .orElseGet(() -> totalCommentService.findAll(slug)));
    }
}
