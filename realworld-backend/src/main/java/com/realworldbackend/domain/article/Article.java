package com.realworldbackend.domain.article;

import com.realworldbackend.domain.article.tag.Tag;
import com.realworldbackend.domain.user.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;

@NamedEntityGraph(name = "with_tagList", attributeNodes = {
        @NamedAttributeNode("tags")
})
@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(of = {"id"})
public class Article {

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();

    @JoinTable(name = "article_favorites",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false))
    @ManyToMany(fetch = LAZY, cascade = PERSIST)
    private Set<User> userFavorited = new HashSet<>();

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Transient
    private boolean isFavorited = false;


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
        this.userFavorited = new HashSet<>(favoritesUsers);
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    protected Article() {

    }

    public Article withFavorited(final User currentUser) {
        isFavorited = userFavorited.contains(currentUser);
        author.viewProfile(currentUser);
        return this;
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
                toSlug(title),
                description,
                body,
                author,
                new ArrayList<>()
        );
    }

    private static String toSlug(
            final String title
    ) {
        StringBuilder slugBuilder = new StringBuilder();

        Arrays.stream(title.split(" "))
                .forEach(word ->
                        slugBuilder.append(word).append("-"));

        return slugBuilder.substring(0, slugBuilder.length() - 1);
    }

    public Article addTags(Set<Tag> tags) {
        this.tags.addAll(tags);
        return this;
    }

    public Set<String> getTags() {
        return this.tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
    }

    public Article update(Optional<String> title, Optional<String> body, Optional<String> description) {
        title.ifPresent(this::changeTitle);
        body.ifPresent(this::changeBody);
        description.ifPresent(this::changeDescription);

        return this;
    }

    private void changeDescription(String newDescription) {
        this.description = newDescription;
    }

    private void changeBody(String newBody) {
        this.body = newBody;
    }

    private void changeTitle(String newTitle) {
        this.title = newTitle;
    }

    public Article addFavorite(User user) {
        userFavorited.add(user);
        return this;
    }

    public void removeFavoritedUser(User viewer) {
        userFavorited.remove(viewer);
    }
}
