package com.realworldbackend.article.api.request;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public record CreateArticleRequest(

        @NotEmpty(message = "Title cannot be empty.")
        String title,

        @NotEmpty(message = "Description cannot be empty.")
        String description,

        @NotEmpty(message = "Body cannot be empty")
        String body,

        List<String> tagList
) {

}
