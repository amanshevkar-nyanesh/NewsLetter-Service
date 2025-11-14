package com.newsletter.repository;

import com.newsletter.model.dao.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    //To Fetch Content Based on TopicID
    List<Content> findByTopicId(Long topicId);
}

