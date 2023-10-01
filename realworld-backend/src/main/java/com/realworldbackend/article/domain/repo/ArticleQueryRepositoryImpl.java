package com.realworldbackend.article.domain.repo;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.realworldbackend.article.api.dto.ArticleFindCondition;
import com.realworldbackend.article.domain.Article;
import com.realworldbackend.article.domain.Tag;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

import static com.realworldbackend.article.domain.QArticle.article;
import static com.realworldbackend.article.domain.QTag.tag;
import static com.realworldbackend.user.domain.QUser.user;

@RequiredArgsConstructor
@Slf4j
public class ArticleQueryRepositoryImpl implements ArticleQueryRepository {

    private final EntityManager em;

    @Override
    public List<Article> search(final ArticleFindCondition condition) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory
                .selectFrom(article)
                .where(
                        contTag(condition.tag()),
                        eqAuthor(condition.author()),
                        contFavorited(condition.favorited())
                )
                .distinct()
                .fetch();
    }

    private BooleanExpression contFavorited(final String favorited) {
        if (StringUtils.isNullOrEmpty(favorited)) {
            return null;
        }

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return article.favoritesUsers.contains(
                Objects.requireNonNull(queryFactory
                        .selectFrom(user)
                        .where(
                                user.username.eq(favorited)
                        )
                        .fetchOne()));
    }

    private BooleanExpression eqAuthor(final String author) {
        if (StringUtils.isNullOrEmpty(author)) {
            return null;
        }

        return user.username.eq(author);
    }

    private BooleanExpression contTag(final String tagName) {
        if (StringUtils.isNullOrEmpty(tagName)) {
            return null;
        }
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        Tag foundTag = queryFactory.selectFrom(tag)
                .where(tag.name.eq(tagName))
                .fetchOne();

        return article.tagList.contains(foundTag);
    }
}