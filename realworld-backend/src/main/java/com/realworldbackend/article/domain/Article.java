package com.realworldbackend.article.domain;

import com.realworldbackend.common.model.BaseEntity;
import com.realworldbackend.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.*;

@NamedEntityGraph(name = "with_tagList", attributeNodes = {
        @NamedAttributeNode("tagList")
})
@Getter
@Entity
@DynamicInsert
@DynamicUpdate
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_title", nullable = false)
    private String title;

    @Column(name = "article_slug", nullable = false)
    private String slug;

    @Column(name = "article_description")
    private String description;

    @Column(name = "article_body")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ElementCollection
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "article_id"))
    private Set<String> tagList = new HashSet<>();

    @OneToMany
    private Set<User> favoritesUsers = new HashSet<>();

    protected Article(
            Long id,
            String title,
            String slug,
            String description,
            String body, User author,
            Collection<String> tagList,
            Collection<User> favoritesUsers
    ) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.body = body;
        this.author = author;
        this.tagList = new HashSet<>(tagList);
        this.favoritesUsers = new HashSet<>(favoritesUsers);
    }

    protected Article() {
    }

    public boolean isFavorited(final User currentUser) {
        return favoritesUsers.contains(currentUser);
    }

    public static Article makeArticle(
            final String title,
            final String description,
            final String body,
            final User author,
            final List<String> tags
    ) {
        return new Article(null,
                title,
                makeSlug(title),
                description,
                body,
                author,
                tags,
                new ArrayList<>()
        );
    }

    private static String makeSlug(final String title) {
        StringBuilder slugBuilder = new StringBuilder();

        Arrays.stream(title.split(" "))
                .forEach(word ->
                        slugBuilder.append(word).append("-"));

        return slugBuilder.substring(0, slugBuilder.length() - 1);
    }

}
