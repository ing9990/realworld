package com.realworldbackend.domain.user;

import com.realworldbackend.domain.article.Article;
import com.realworldbackend.application.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import static jakarta.persistence.CascadeType.REMOVE;

@Entity
@Table(name = "user_tb")
@Where(clause = "user_status = 'ACTIVE'")
@SQLDelete(sql = "UPDATE user SET user_status = 'DELETED' WHERE id = ?")
@Getter
public class User {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Embedded
    private Avatar avatar = new Avatar();


    @JoinTable(name = "user_follow", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), inverseJoinColumns = @JoinColumn(name = "followee_id", referencedColumnName = "user_id"))
    @OneToMany(cascade = REMOVE)
    private Set<User> followers = new HashSet<>();

    @ManyToMany(mappedBy = "userFavorited")
    private Set<Article> articleFavorited = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UserStatus userStatus = UserStatus.ACTIVE;

    @CreatedDate
    @Column(name = "create_at", updatable = false, nullable = false)
    private Instant createdAt;


    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;

    protected User() {

    }

    private User(final String username, final String email, final String password) {
        checkEmailFormat(email);

        this.email = email;
        this.password = password;
        this.userStatus = UserStatus.ACTIVE;
        this.createdAt = Instant.now();
        this.modifiedAt = Instant.now();
        this.avatar = Avatar.withUsername(username);
    }

    public Avatar viewProfile(User viewer) {
        return this.avatar.withFollowing(followers.contains(viewer));
    }

    public static User registration(final String username, final String email, final String encodedPassword) {
        return new User(username, email, encodedPassword);
    }

    private void checkEmailFormat(final String email) {
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new InvalidEmailFormatException(ErrorCode.INVALID_EMAIL);
        }
    }

    public User update(final Optional<String> email, final Optional<String> username, final Optional<String> password, final Optional<String> bio, final Optional<String> image) {
        email.ifPresent(this::changeEmail);
        password.ifPresent(this::changePassword);
        avatar.updateProfile(username, bio, image);
        return this;
    }

    private void changePassword(String password) {
        this.password = password;
    }

    private void changeEmail(String email) {
        this.email = email;
    }

    protected User followTargetUser(final User followee) {
        followers.add(followee);
        return this;
    }

    protected User unFollowTargetUser(final User followee) {
        followers.remove(followee);
        return this;
    }

    public void addFavoritedArticle(final Article article) {
        articleFavorited.add(article);
    }

    public void removeFavoritedAritcle(final Article article) {
        articleFavorited.remove(article);
    }
}