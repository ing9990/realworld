package com.realworldbackend.application.api.articles.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.status;

@RequestMapping("/api/tags")
@RestController
@RequiredArgsConstructor
public class TagApi {

    private final TotalTagService totalTagService;

    @GetMapping
    public ResponseEntity<TagResponse> tags() {
        return status(HttpStatus.OK).body(totalTagService.findAll());
    }
}
