package com.realworldbackend.application.api.articles.comment;

import com.realworldbackend.application.api.user.profile.ProfileResponse;
import com.realworldbackend.domain.article.comment.Comment;
import lombok.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class MultiCommentResponse {

    private List<SingleCommentResponse> comments;

    static MultiCommentResponse from(Collection<Comment> comments) {
        return new MultiCommentResponse(comments.stream()
                .map(SingleCommentResponse::from)
                .toList());
    }

    @AllArgsConstructor
    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    static class SingleCommentResponse {

        private Long id;
        private ZonedDateTime createdAt;
        private ZonedDateTime updatedAt;
        private String body;
        private ProfileResponse author;

        public static SingleCommentResponse from(Comment comment) {
            return SingleCommentResponse.builder()
                    .id(comment.getId())
                    .createdAt(comment.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")))
                    .updatedAt(comment.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")))
                    .body(comment.getBody())
                    .author(ProfileResponse.fromAvatar(comment.getAuthor().getAvatar()))
                    .build();
        }
    }
}
