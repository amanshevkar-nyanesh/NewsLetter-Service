package com.newsletter.service;

import com.newsletter.model.dao.Topic;
import com.newsletter.model.request.TopicRequest;
import com.newsletter.repository.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    public Topic createTopic(TopicRequest topicRequest) {
        if (topicRepository.existsByName(topicRequest.getName())) {
            throw new IllegalArgumentException("Topic with name '" + topicRequest.getName() + "' already exists");
        }
        Topic topic = new Topic();
        topic.setName(topicRequest.getName());
        topic.setDescription(topicRequest.getDescription());
        return topicRepository.save(topic);
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topic getTopicById(Long id) {
        return topicRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteTopic(Long id) {
        if (topicRepository.existsById(id)) {
            topicRepository.deleteById(id);
            log.info("Deleted topic with id: {}", id);
        } else {
            throw new IllegalArgumentException("Topic with id " + id + " not found");
        }
    }

}
