package com.realworldbackend.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("select count(f) > 0 from Follow f where f.user = ?1 and f.targetUser = ?2")
    boolean existsByUserAndFollowing(User user, User targetUser);

    @Modifying
    @Query("delete from Follow f where f.user = ?1 and f.targetUser = ?2")
    void deleteByUserAndTargetUser(User user, User target);
}
