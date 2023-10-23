package com.realworldbackend.domain.article;

import com.realworldbackend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public List<Article> findAll(final Pageable pageable) {
        return articleRepository.findAll(pageable).getContent();
    }


    @Transactional(readOnly = true)
    public List<Article> findAll(final ArticleCondition articleCondition) {
        return articleRepository.findAllByCondition(articleCondition.getTag(), articleCondition.getFavorited(), articleCondition.getAuthor(), articleCondition.getOffset(), articleCondition.getLimit());
    }

    public List<Article> findAll(String author, String favorited, String tag, int limit, int offset) {
        return articleRepository.findAllByCondition(tag, favorited, author, offset, limit);
    }
}

