package com.realworldbackend.article.domain;

import com.realworldbackend.common.model.BaseEntity;
import com.realworldbackend.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.*;

@NamedEntityGraph(name = "with_tagList", attributeNodes = {
        @NamedAttributeNode("tags")
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

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "article_favorites",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> favoritesUsers = new HashSet<>();

    protected Article(
            final Long id,
            final String title,
            final String slug,
            final String description,
            final String body,
            final User author,
            final Collection<User> favoritesUsers
    ) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.body = body;
        this.author = author;
        this.tags = new HashSet<>();
        this.favoritesUsers = new HashSet<>(favoritesUsers);
    }

    protected Article() {
    }

    public boolean isFavorited(final User currentUser) {
        return favoritesUsers.contains(currentUser);
    }

    public boolean hasTag(
            final String tag
    ) {
        return this.tags.stream().anyMatch(t -> t.getName().equals(tag));
    }

    public static Article makeArticle(
            final String title,
            final String description,
            final String body,
            final User author
    ) {
        return new Article(null,
                title,
                makeSlug(title),
                description,
                body,
                author,
                new ArrayList<>()
        );
    }

    private static String makeSlug(
            final String title
    ) {
        StringBuilder slugBuilder = new StringBuilder();

        Arrays.stream(title.split(" "))
                .forEach(word ->
                        slugBuilder.append(word).append("-"));

        return slugBuilder.substring(0, slugBuilder.length() - 1);
    }

    public void addTags(Set<Tag> tags) {
        this.tags.addAll(tags);
    }
}
