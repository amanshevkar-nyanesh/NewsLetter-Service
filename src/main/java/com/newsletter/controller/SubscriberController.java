package com.newsletter.controller;

import com.newsletter.model.request.SubscriberRequest;
import com.newsletter.model.response.SubscriberResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/subscribers")
public class SubscriberController {

    //To create a subscriber in the system
    @PostMapping
    public SubscriberResponse createSubscriber(@RequestBody SubscriberRequest request) {
        SubscriberResponse response = new SubscriberResponse();
        return response;
    }

    //To fetch all subscribers from the system
    @GetMapping
    public SubscriberResponse getSubscribers() {
        SubscriberResponse response = new SubscriberResponse();
        return response;
    }

    //To fetch a subscriber from the system
    @GetMapping("/{id}")
    public SubscriberResponse getSubscriber(@PathVariable("id") String id) {
        SubscriberResponse response = new SubscriberResponse();
        return response;
    }

    //To delete a subscriber from the system
    @DeleteMapping("/{id}/deactivate")
    public SubscriberResponse deactivateSubscriber(@PathVariable("id") String id) {
        SubscriberResponse response = new SubscriberResponse();
        return response;
    }

    //To subscribe a subscriber to a topic
    @PostMapping("/{subsId}/subscribe/{topicId}")
    public SubscriberResponse subscribeSubscriber(@PathVariable("subsId") String subsId, @PathVariable("topicId") Long topicId) {
        SubscriberResponse response = new SubscriberResponse();
        return response;
    }

    //To unsubscribe a subscriber from a topic
    @PostMapping("/{subsId}/unsubscribe/{topicId}")
    public SubscriberResponse unsubscribeSubscriber(@PathVariable("subsId") String subsId, @PathVariable("topicId") Long topicId) {
        SubscriberResponse response = new SubscriberResponse();
        return response;
    }
}
