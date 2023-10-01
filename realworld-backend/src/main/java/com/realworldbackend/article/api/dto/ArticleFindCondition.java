package com.realworldbackend.article.api.dto;

import com.querydsl.core.util.StringUtils;

public record ArticleFindCondition(
        String tag,
        String author,
        String favorited
) {
}
