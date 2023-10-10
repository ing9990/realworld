package com.realworldbackend.domain.article;

import com.realworldbackend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article save(
            final String title,
            final String description,
            final String body,
            final User author
    ) {
        return articleRepository.save(Article.makeArticle(title, description, body, author));
    }
}
