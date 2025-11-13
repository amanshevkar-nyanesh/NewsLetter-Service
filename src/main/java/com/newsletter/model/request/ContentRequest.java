package com.newsletter.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -5734040513570462152L;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content Data is required")
    private String text;

    @NotNull(message = "Topic ID is required")
    private Long topicId;

    @NotNull(message = "Scheduled time is required")
    private LocalDateTime scheduledTime;

}
