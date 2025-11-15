package com.newsletter.repository;

import com.newsletter.model.dao.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    //To Fetch Content Based on TopicID
    List<Content> findByTopicId(Long topicId);

    //To Fetch unsent content that should be sent now or in the past
    //Eagerly fetch topic and subscribers to avoid LazyInitializationException
    @Query("SELECT DISTINCT c FROM Content c " +
            "LEFT JOIN FETCH c.topic t " +
            "LEFT JOIN FETCH t.subscribers s " +
            "WHERE c.isSent = false AND c.scheduledTime <= :currentTime")
    List<Content> findUnsentContentByScheduledTime(@Param("currentTime") LocalDateTime currentTime);
}

