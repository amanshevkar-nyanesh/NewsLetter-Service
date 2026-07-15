package com.newsletter.repository;

import com.newsletter.model.dao.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    //To fetch topic by name.
    boolean existsByName(String name);
}

