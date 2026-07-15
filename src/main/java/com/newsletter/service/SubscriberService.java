package com.newsletter.service;

import com.newsletter.model.dao.Subscriber;
import com.newsletter.model.dao.Topic;
import com.newsletter.model.request.SubscriberRequest;
import com.newsletter.repository.SubscriberRepository;
import com.newsletter.repository.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class SubscriberService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private TopicRepository topicRepository;

    public Subscriber createSubscriber(SubscriberRequest request) {
        if (subscriberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Subscriber with email '" + request.getEmail() + "' already exists");
        }
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail(request.getEmail());
        subscriber.setName(request.getName());
        return subscriberRepository.save(subscriber);
    }

    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    public Subscriber getSubscriberById(Long id) {
        return subscriberRepository.findById(id).orElse(null);
    }

    @Transactional
    public Subscriber subscribeToTopic(Long subscriberId, Long topicId) {
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new IllegalArgumentException("Subscriber not found with id: " + subscriberId));

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found with id: " + topicId));

        if (!subscriber.getSubscribedTopics().contains(topic)) {
            // Only modify the owning side (Subscriber) - JPA will handle the inverse side
            subscriber.getSubscribedTopics().add(topic);
            subscriberRepository.save(subscriber);
            log.info("Subscriber {} subscribed to topic {}", subscriber.getEmail(), topic.getName());
        }

        // Return a fresh fetch to avoid lazy loading issues
        return subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new IllegalArgumentException("Subscriber not found with id: " + subscriberId));
    }

    @Transactional
    public Subscriber unsubscribeFromTopic(Long subscriberId, Long topicId) {
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new IllegalArgumentException("Subscriber not found with id: " + subscriberId));

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found with id: " + topicId));

        if (subscriber.getSubscribedTopics().contains(topic)) {
            // Only modify the owning side (Subscriber) - JPA will handle the inverse side
            subscriber.getSubscribedTopics().remove(topic);
            subscriberRepository.save(subscriber);
            log.info("Subscriber {} unsubscribed from topic {}", subscriber.getEmail(), topic.getName());
        }

        // Return a fresh fetch to avoid lazy loading issues
        return subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new IllegalArgumentException("Subscriber not found with id: " + subscriberId));
    }

    @Transactional
    public void deactivateSubscriber(Long id) {
        Subscriber subscriber = subscriberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subscriber not found with id: " + id));
        subscriber.setIsActive(false);
        subscriberRepository.save(subscriber);
        log.info("Deactivated subscriber: {}", subscriber.getEmail());
    }
}
