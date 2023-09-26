package com.realworldbackend.article.domain;

import com.realworldbackend.common.exception.BusinessException;
import com.realworldbackend.common.exception.ErrorCode;

public class ArticleNotFoundException extends BusinessException {

    public ArticleNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ArticleNotFoundException() {
        this(ErrorCode.ARTICLE_NOT_FOUND);
    }

    public ArticleNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
