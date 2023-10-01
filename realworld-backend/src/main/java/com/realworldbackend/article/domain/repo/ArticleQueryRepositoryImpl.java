package com.realworldbackend.article.domain.repo;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import com.realworldbackend.article.api.dto.ArticleFindCondition;
import com.realworldbackend.article.domain.Article;
import com.realworldbackend.article.domain.QTag;
import com.realworldbackend.user.domain.QUser;
import org.springframework.stereotype.Repository;

import static com.realworldbackend.article.domain.QArticle.article;

@Repository
public class ArticleQueryRepositoryImpl implements ArticleQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ArticleQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Article> findAll(final ArticleFindCondition articleFindCondition) {
        return queryFactory.selectDistinct(
                article
                )
                .from(article)
                .leftJoin(article.tags, QTag.tag)
                .leftJoin(article.author, new QUser("articles_author"))
                .leftJoin(article.favoritesUsers, QUser.user)
                .where(
                        eqTagName(articleFindCondition.tag()),
                        eqFavoritesName(articleFindCondition.favorited()),
                        eqAuthorName(articleFindCondition.author())
                )
                .offset(articleFindCondition.getOffset())
                .limit(articleFindCondition.getLimit())
                .fetch();
    }

    private BooleanExpression eqTagName(String tagName) {
        return tagName == null ? null : QTag.tag.name.eq(tagName);
    }

    private BooleanExpression eqAuthorName(String authorName) {
        return authorName == null ? null : article.author.username.eq(authorName);
    }

    private BooleanExpression eqFavoritesName(String favoritesName) {
        return favoritesName == null ? null : QUser.user.username.eq(favoritesName);
    }
}
