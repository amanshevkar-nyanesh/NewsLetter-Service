package com.newsletter.controller;

import com.newsletter.model.response.ContentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/contents")
public class ContentController {

    //To create a content in the system
    @PostMapping
    public ContentResponse createContent(@RequestBody ContentResponse request) {
        ContentResponse response = new ContentResponse();
        return response;
    }

    //To fetch all contents from the system
    @GetMapping
    public ContentResponse getContents() {
        ContentResponse response = new ContentResponse();
        return response;
    }

    //To fetch a content from the system
    @GetMapping("/{id}")
    public ContentResponse getContent(@PathVariable("id") Long id) {
        ContentResponse response = new ContentResponse();
        return response;
    }

    //To delete a content from the system
    @DeleteMapping("/{id}")
    public ContentResponse deleteContent(@PathVariable("id") Long id) {
        ContentResponse response = new ContentResponse();
        return response;
    }

    //To fetch a content by topic id
    @GetMapping("/topic/{topicId}")
    public ContentResponse getContentByTopicId(@PathVariable("topicId") Long topicId) {
        ContentResponse response = new ContentResponse();
        return response;
    }
}
