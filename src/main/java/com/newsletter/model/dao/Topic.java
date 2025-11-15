package com.newsletter.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "topics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"contents", "subscribers"})
@EqualsAndHashCode(exclude = {"contents", "subscribers"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("topic")
    private Set<Content> contents = new HashSet<>();

    @ManyToMany(mappedBy = "subscribedTopics", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("subscribedTopics")
    private Set<Subscriber> subscribers = new HashSet<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
