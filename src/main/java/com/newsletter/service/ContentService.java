package com.newsletter.service;

import com.newsletter.model.dao.Content;
import com.newsletter.model.dao.Topic;
import com.newsletter.model.request.ContentRequest;
import com.newsletter.repository.ContentRepository;
import com.newsletter.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContentService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ContentRepository contentRepository;

    public Content createContent(ContentRequest request) {
        Topic topic = topicRepository.findById(request.getTopicId()).orElseThrow(() -> new IllegalArgumentException("Topic not found"));
        Content content = new Content();
        content.setTopic(topic);
        content.setText(request.getText());
        content.setTitle(request.getTitle());
        content.setScheduledTime(request.getScheduledTime());
        Content savedContent = contentRepository.save(content);
        return savedContent;
    }

    public List<Content> getAllContents() {
        return contentRepository.findAll();
    }

    public List<Content> getContentsByTopic(Long topicId) {
        return contentRepository.findByTopicId(topicId);
    }

    public Content getContentById(Long id) {
        return contentRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteContent(Long id) {
        if (contentRepository.existsById(id)) {
            contentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Content with id " + id + " not found");
        }
    }
}
