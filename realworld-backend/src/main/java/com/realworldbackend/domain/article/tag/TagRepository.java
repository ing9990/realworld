package com.realworldbackend.domain.article.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("select t from Tag t where t.name in(?1)")
    Set<Tag> findByTagNameIn(Collection<String> tags);

    @Query("select t from Tag t where t.name not in ?1")
    Set<Tag> findByNameNotIn(Collection<String> tags);
}
