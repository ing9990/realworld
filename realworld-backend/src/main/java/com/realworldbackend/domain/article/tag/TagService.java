package com.realworldbackend.domain.article.tag;

import com.realworldbackend.application.api.articles.tag.TagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Set<Tag> saveIfNotExists(Set<String> tagList) {
        Set<Tag> existingTags = tagRepository.findByTagNameIn(tagList);

        Set<String> existingTagNames = existingTags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        Set<Tag> newTags = tagList.stream()
                .filter(tagName -> !existingTagNames.contains(tagName))
                .map(Tag::new)
                .collect(Collectors.toSet());
        tagRepository.saveAll(newTags);
        existingTags.addAll(newTags);
        return existingTags;
    }

    public Collection<Tag> findAll() {
        return tagRepository.findAll();
    }
}
