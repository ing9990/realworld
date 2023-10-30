package com.realworldbackend.application.api.articles.comment;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.realworldbackend.application.api.user.profile.ProfileResponse;
import com.realworldbackend.domain.article.comment.Comment;

import lombok.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonTypeName("comment")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
class CommentResponse {

    private Long id;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private String body;
    private ProfileResponse author;

    static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .createdAt(comment.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")))
                .updatedAt(comment.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")))
                .body(comment.getBody())
                .author(ProfileResponse.fromAvatar(comment.getAuthor().getAvatar()))
                .build();
    }
}
