package com.realworldbackend.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select (count(u) > 0) from User u where u.avatar.username = ?1")
    boolean existsByUsername(String username);

    @Query("select (count(u) > 0) from User u where u.email = ?1")
    boolean existsByEmail(String email);

    @Query("select u from User u where u.email = ?1")
    Optional<User> findUserByEmail(String email);

    @Query("select u from User u where u.id = ?1")
    Optional<User> findUserByUserId(Long userId);

    @Query("select u from User u where u.avatar.username = ?1")
    Optional<User> findUserByUsername(String username);

    @Query("select u,f from User u join u.followers f where u = ?1")
    List<User> findIFollowed(User user);
}
