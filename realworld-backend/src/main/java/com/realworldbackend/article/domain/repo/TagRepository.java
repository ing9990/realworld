package com.realworldbackend.article.domain.repo;

import com.realworldbackend.article.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("select t from Tag t where t.name in(?1)")
    List<Tag> findByTagNameIn(List<String> tags);
}
