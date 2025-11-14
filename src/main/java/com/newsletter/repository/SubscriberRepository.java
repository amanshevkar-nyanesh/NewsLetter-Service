package com.newsletter.repository;

import com.newsletter.model.dao.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    //To check if Subscriber with this email already exits
    boolean existsByEmail(String email);
}

