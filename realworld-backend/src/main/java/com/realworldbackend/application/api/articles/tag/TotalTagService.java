package com.realworldbackend.application.api.articles.tag;

import com.realworldbackend.domain.article.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TotalTagService {

    private final TagService tagService;

    public TagResponse findAll() {
        return TagResponse.of(tagService.findAll());
    }
}
