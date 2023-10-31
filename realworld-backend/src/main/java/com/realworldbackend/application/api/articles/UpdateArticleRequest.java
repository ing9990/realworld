package com.realworldbackend.application.api.articles;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Getter
@NoArgsConstructor
@JsonTypeName("article")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
class UpdateArticleRequest {

    private String title;
    private String description;
    private String body;

    public Optional<String> getTitle() {
        return Optional.ofNullable(title);
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<String> getBody() {
        return Optional.ofNullable(body);
    }
}
