package com.newsletter.controller;

import com.newsletter.Utils.SubscriberHelper;
import com.newsletter.model.dao.Subscriber;
import com.newsletter.model.request.SubscriberRequest;
import com.newsletter.model.response.SubscriberResponse;
import com.newsletter.service.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/subscribers")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private SubscriberHelper subscriberHelper;

    //To create a subscriber in the system
    @PostMapping
    public SubscriberResponse createSubscriber(@RequestBody SubscriberRequest request) {
        try {
            Subscriber  subscriber = subscriberService.createSubscriber(request);
            log.info("Subscriber created: {}", subscriber);
            return subscriberHelper.createSubscriberResponse(subscriber, true);
        }  catch (Exception e) {
            log.error("Subscriber creation failed: {}", e.getMessage());
            return subscriberHelper.createSubscriberResponse(null, false);
        }
    }

    //To fetch all subscribers from the system
    @GetMapping
    public SubscriberResponse getSubscribers() {
        List<Subscriber> subscribers = subscriberService.getAllSubscribers();
        return subscriberHelper.getAllSubscribersResponse(subscribers);
    }

    //To fetch a subscriber from the system
    @GetMapping("/{id}")
    public SubscriberResponse getSubscriber(@PathVariable("id") Long id) {
        Subscriber subscriber = subscriberService.getSubscriberById(id);
        log.info("Subscriber found: {}", subscriber);
        return subscriberHelper.getSubscriberResponse(subscriber);
    }

    //To delete a subscriber from the system
    @DeleteMapping("/{id}/deactivate")
    public SubscriberResponse deactivateSubscriber(@PathVariable("id") Long id) {
        try {
            subscriberService.deactivateSubscriber(id);
            log.info("Subscriber deactivated: {}", id);
            return subscriberHelper.getDeactiveSubscriberResponse(true);
        } catch (Exception e) {
            log.error("Subscriber deactivation failed: {}", e.getMessage());
            return subscriberHelper.getDeactiveSubscriberResponse(false);
        }
    }

    //To subscribe a subscriber to a topic
    @PostMapping("/{subsId}/subscribe/{topicId}")
    public SubscriberResponse subscribeSubscriber(@PathVariable("subsId") Long subsId, @PathVariable("topicId") Long topicId) {
        try {
            Subscriber subscriber = subscriberService.subscribeToTopic(subsId, topicId);
            log.info("Subscriber subscribed: {}", subscriber);
            return subscriberHelper.getSubscriberStatusResponse(subscriber, true);
        } catch (Exception e) {
            log.error("Subscriber subscribe failed: {}", e.getMessage());
            return subscriberHelper.getSubscriberStatusResponse(null, false);
        }
    }

    //To unsubscribe a subscriber from a topic
    @PostMapping("/{subsId}/unsubscribe/{topicId}")
    public SubscriberResponse unsubscribeSubscriber(@PathVariable("subsId") Long subsId, @PathVariable("topicId") Long topicId) {
        try {
            Subscriber subscriber = subscriberService.unsubscribeFromTopic(subsId, topicId);
            log.info("Subscriber unsubscribed: {}", subscriber);
            return subscriberHelper.getSubscriberStatusResponse(subscriber, true);
        } catch (Exception e) {
            log.error("Subscriber unsubscribe failed: {}", e.getMessage());
            return subscriberHelper.getSubscriberStatusResponse(null, false);
        }
    }
}
