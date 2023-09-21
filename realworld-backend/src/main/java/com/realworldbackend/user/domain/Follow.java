package com.realworldbackend.user.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "follow")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "following_user_id")
    private User targetUser;

    public Follow(Long id, User user, User targetUser) {
        this.id = id;
        this.user = user;
        this.targetUser = targetUser;
    }

    protected Follow() {
    }

    public Follow(User user, User targetUser) {
        this.user = user;
        this.targetUser = targetUser;
    }

    public Long getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public User getTargetUser() {
        return this.targetUser;
    }
}
