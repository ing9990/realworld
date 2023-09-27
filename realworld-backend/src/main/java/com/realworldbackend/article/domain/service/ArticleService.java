package com.realworldbackend.article.domain.service;

import com.realworldbackend.article.domain.ArticleVO;
import com.realworldbackend.article.api.dto.response.SingleArticleResponse;
import com.realworldbackend.article.domain.Article;
import com.realworldbackend.article.domain.ArticleNotFoundException;
import com.realworldbackend.article.domain.ArticleRepository;
import com.realworldbackend.user.domain.User;
import com.realworldbackend.user.domain.UserRepository;
import com.realworldbackend.user.domain.service.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public Long makeArticle(User author, String title, String description, String body, List<String> tags) {
        Article article = articleRepository.save(Article.makeArticle(title, description, body, author, tags));

        return article.getId();
    }

    @Transactional(readOnly = true)
    public SingleArticleResponse getArticle(Long articleId) {
        Article article = articleRepository.findArticleById(articleId);

        return SingleArticleResponse.from(article);
    }
}
