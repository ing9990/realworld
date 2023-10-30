package com.realworldbackend.application.api.articles.tag;

import com.realworldbackend.domain.article.tag.Tag;
import lombok.*;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagResponse {

    private Set<String> tags;

    public static TagResponse of(Collection<Tag> tags) {
        return TagResponse.builder()
                .tags(tags.stream().map(Tag::getName)
                        .collect(Collectors.toSet()))
                .build();
    }
}
