package com.realworldbackend.article.api.response;

import com.realworldbackend.user.domain.User;

public record AuthorResponse(

) {

    public static AuthorResponse from(User author) {
        return null;
    }
}
