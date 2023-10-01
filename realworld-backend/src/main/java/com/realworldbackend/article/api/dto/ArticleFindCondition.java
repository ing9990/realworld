package com.realworldbackend.article.api.dto;

public record ArticleFindCondition(
        String tag,
        String author,
        String favorited,
        Integer offset,
        Integer limit
) {

    public int getOffset() {
        return offset == null ? 0 : offset;
    }

    public int getLimit() {
        return limit == null ? 10 : limit;
    }

}
