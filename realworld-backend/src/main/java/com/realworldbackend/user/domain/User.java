package com.realworldbackend.user.domain;

import com.realworldbackend.common.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Entity
@Table(name = "user")
@Where(clause = "user_status = 'ACTIVE'")
@SQLDelete(sql = "UPDATE user SET user_status = 'DELETED' WHERE id = ?")
@Getter
public class User {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Embedded
    private Avatar avatar = new Avatar();

    @OneToMany
    private Set<User> followers = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UserStatus userStatus = UserStatus.ACTIVE;

    @CreatedDate
    @Column(name = "create_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;


    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    protected User() {

    }

    public User(Long id, final String username, final String email, final String password) {
        checkEmailFormat(email);
        checkUsernameFormat(username);

        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userStatus = UserStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();

        this.avatar = new Avatar();
    }

    public static User registration(final String username, final String email, final String password) {
        return new User(null, username, email, password);
    }

    private void checkUsernameFormat(final String username) {
        // TODO document why this method is empty
    }

    private void checkEmailFormat(final String email) {
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new InvalidEmailFormatException(ErrorCode.INVALID_EMAIL);
        }
    }

    public void update(final String email, final String username, final String password, final String bio, final String image) {
        this.email = email == null ? this.email : email;
        this.username = username == null ? this.username : username;
        this.password = "".equals(password) || password == null ? this.password : password;

        if (this.avatar == null) {
            this.avatar = new Avatar();
        }

        this.avatar.updateProfile(bio, image);
    }

    public boolean isFollow(
            final User targetUser
    ) {
        return this.followers.contains(targetUser);
    }

    public void followTargetUser(
            final User currentUser
    ) {
        if (this.followers == null) {
            this.followers = new HashSet<>();
        }
        this.followers.add(currentUser);
    }

    public void unFollowTargetUser(
            final User currentUser
    ) {
        if (this.followers == null) {
            this.followers = new HashSet<>();
        }
        this.followers.remove(currentUser);
    }
}