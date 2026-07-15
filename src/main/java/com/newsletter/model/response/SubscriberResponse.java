package com.newsletter.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.newsletter.model.dao.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriberResponse extends Result implements Serializable {
    @Serial
    private static final long serialVersionUID = -2103695310321185805L;
    private Long id;
    private String email;
    private String name;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private Set<Topic> subscribedTopics;
    private Object data;
}
