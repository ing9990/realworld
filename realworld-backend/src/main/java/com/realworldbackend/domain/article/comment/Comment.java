package com.realworldbackend.domain.article.comment;

import com.realworldbackend.domain.article.Article;
import com.realworldbackend.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", updatable = false, nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "article_id", updatable = false, nullable = false)
    private Article article;

    @Column(name = "comment_body", nullable = false)
    private String body;

    @CreationTimestamp
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    protected Comment(User author, Article article, String body) {
        this.author = author;
        this.article = article;
        this.body = body;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public static Comment createComment(User author, Article article, String body) {
        return new Comment(author, article, body);
    }
}
