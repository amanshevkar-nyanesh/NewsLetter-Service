package com.newsletter.controller;

import com.newsletter.model.request.TopicRequest;
import com.newsletter.model.response.TopicResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/topics")
public class TopicController {

    //To create a topic in the system
    @PostMapping
    public TopicResponse createTopic(@RequestBody TopicRequest request) {
        TopicResponse response = new TopicResponse();
        return response;
    }

    //To fetch all topics from the system
    @GetMapping
    public TopicResponse getTopics() {
        TopicResponse response = new TopicResponse();
        return response;
    }

    //To fetch a topic from the system
    @GetMapping("/{id}")
    public TopicResponse getTopic(@PathVariable("id") Long id) {
        TopicResponse response = new TopicResponse();
        return response;
    }

    //To delete a topic from the system
    @DeleteMapping("/{id}")
    public TopicResponse deleteTopic(@PathVariable("id") Long id) {
        TopicResponse response = new TopicResponse();
        return response;
    }

}
