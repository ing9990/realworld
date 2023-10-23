package com.realworldbackend.domain.article;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleCondition {

    private String author;

    private String favorited;

    private String tag;

    private int limit = 10;

    private int offset = 0;
}
