package com.realworldbackend.domain.article;

import com.realworldbackend.application.exception.BusinessException;
import com.realworldbackend.application.exception.ErrorCode;

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
