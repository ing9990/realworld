package com.realworldbackend.application.api.articles;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("article")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
class CreateArticleRequest {
    @NotEmpty
    private String title;
    @NotEmpty
    private String body;
    @NotEmpty
    private String description;

    private Set<String> tagList = new HashSet<>();
}
