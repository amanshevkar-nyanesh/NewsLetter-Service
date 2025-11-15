package com.newsletter.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subscribers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "subscribedTopics")
@EqualsAndHashCode(exclude = "subscribedTopics")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "subscriber_topics",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    @JsonIgnoreProperties("subscribers")
    private Set<Topic> subscribedTopics = new HashSet<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean isActive = true;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (isActive == null) {
            isActive = true;
        }
    }
}

