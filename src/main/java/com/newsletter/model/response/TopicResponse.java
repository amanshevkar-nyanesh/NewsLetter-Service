package com.newsletter.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopicResponse extends Result implements Serializable {
    @Serial
    private static final long serialVersionUID = -317016933600520904L;

    private Long id;
    private String name;
    private Object data;
    private String description;
    private LocalDateTime createdAt;

}
