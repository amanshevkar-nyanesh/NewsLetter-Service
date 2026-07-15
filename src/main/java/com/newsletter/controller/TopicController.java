package com.newsletter.controller;

import com.newsletter.Utils.TopicHelper;
import com.newsletter.model.dao.Topic;
import com.newsletter.model.request.TopicRequest;
import com.newsletter.model.response.TopicResponse;
import com.newsletter.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicHelper topicHelper;


    //To create a topic in the system
    @PostMapping
    public TopicResponse createTopic(@RequestBody TopicRequest request) {
        try {
            Topic topic = topicService.createTopic(request);
            log.info("Topic Created Successfully for topic :{} with id :{}", topic.getName(), topic.getId());
            return topicHelper.createTopicResponse(topic, true);
        } catch (Exception e) {
            log.error("Topic Creation Failed:{} ", e.getMessage());
            return topicHelper.createTopicResponse(null, false);
        }
    }

    //To fetch all topics from the system
    @GetMapping
    public TopicResponse getTopics() {
        List<Topic> topics = topicService.getAllTopics();
        log.info("Found :{} topics", topics.size());
        return topicHelper.getAllTopicsResponse(topics);
    }

    //To fetch a topic from the system
    @GetMapping("/{id}")
    public TopicResponse getTopic(@PathVariable("id") Long id) {
        Topic topic = topicService.getTopicById(id);
        log.info("Topic found: {}", topic);
        return topicHelper.getTopicResponse(topic);
    }

    //To delete a topic from the system
    @DeleteMapping("/{id}")
    public TopicResponse deleteTopic(@PathVariable("id") Long id) {
        try {
            topicService.deleteTopic(id);
            log.info("Topic Deleted Successfully for topic id :{}", id);
            return topicHelper.getDeleteTopicResponse(true);
        } catch (Exception e) {
            log.error("Topic Deletion Failed :{}", e.getMessage());
            return topicHelper.getDeleteTopicResponse(false);
        }
    }

}
