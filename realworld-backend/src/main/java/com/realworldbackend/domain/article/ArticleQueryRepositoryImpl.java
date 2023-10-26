package com.realworldbackend.domain.article;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.realworldbackend.domain.article.tag.QTag;
import com.realworldbackend.domain.user.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.realworldbackend.domain.article.QArticle.article;

@Repository
@RequiredArgsConstructor
public class ArticleQueryRepositoryImpl implements ArticleQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Article> findAllByCondition(String tagName, String favoritesName, String authorName, int offset, int limit) {
        List<Long> articleIds = queryFactory.selectDistinct(article.id)
                .from(article)
                .leftJoin(article.tags, QTag.tag)
                .leftJoin(article.author, new QUser("user_author"))
                .leftJoin(article.userFavorited, QUser.user)
                .where(
                        eqTagName(tagName),
                        eqFavoritesName(favoritesName),
                        eqAuthorName(authorName))
                .offset(offset)
                .limit(limit)
                .fetch();

        return findAllById(articleIds);
    }

    private List<Article> findAllById(List<Long> idList) {
        return queryFactory.selectFrom(article)
                .innerJoin(article.author, QUser.user).fetchJoin()
                .where(article.id.in(idList))
                .fetch();
    }

    private Predicate eqTagName(String tagName) {
        if (tagName == null) {
            return null;
        }
        return QTag.tag.name.eq(tagName);
    }

    private BooleanExpression eqAuthorName(String authorName) {
        if (authorName == null) {
            return null;
        }
        return article.author.avatar.username.eq(authorName);
    }

    private BooleanExpression eqFavoritesName(String favoritesName) {
        if (favoritesName == null) {
            return null;
        }
        return QUser.user.avatar.username.eq(favoritesName);
    }
}
