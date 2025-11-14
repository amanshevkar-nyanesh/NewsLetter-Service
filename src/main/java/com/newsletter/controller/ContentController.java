package com.newsletter.controller;

import com.newsletter.Utils.ContentHelper;
import com.newsletter.model.dao.Content;
import com.newsletter.model.request.ContentRequest;
import com.newsletter.model.response.ContentResponse;
import com.newsletter.repository.ContentRepository;
import com.newsletter.service.ContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/contents")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private ContentHelper contentHelper;

    //To create a content in the system
    @PostMapping
    public ContentResponse createContent(@RequestBody ContentRequest request) {
        try {
            Content content = contentService.createContent(request);
            log.info("Created content with id :{}", content.getId());
            return contentHelper.getContentCreatedResponse(content, true);
        } catch (Exception e) {
            log.error("Create content failed: {}", e.getMessage());
            return contentHelper.getContentCreatedResponse(null, false);
        }
    }

    //To fetch all contents from the system
    @GetMapping
    public ContentResponse getContents() {
        List<Content> contents = contentService.getAllContents();
        log.info("Found :{} contents", contents.size());
        return contentHelper.getContentsResponse(contents, false);
    }

    //To fetch a content from the system
    @GetMapping("/{id}")
    public ContentResponse getContent(@PathVariable("id") Long id) {
        Content content = contentService.getContentById(id);
        return contentHelper.getContentsResponse(content, true);
    }

    //To delete a content from the system
    @DeleteMapping("/{id}")
    public ContentResponse deleteContent(@PathVariable("id") Long id) {
        try {
            contentService.deleteContent(id);
            log.info("Deleted content with id :{}", id);
            return contentHelper.getDeleteContentResponse(true);
        } catch (Exception e){
            log.error("Delete content failed: {}", e.getMessage());
            return contentHelper.getDeleteContentResponse(false);
        }
    }

    //To fetch a content by topic id
    @GetMapping("/topic/{topicId}")
    public ContentResponse getContentByTopicId(@PathVariable("topicId") Long topicId) {
        List<Content> contents = contentService.getContentsByTopic(topicId);
        log.info("Found :{} contents based on topicID :{}", contents.size(), topicId);
        return contentHelper.getContentsResponse(contents, true );
    }
}
