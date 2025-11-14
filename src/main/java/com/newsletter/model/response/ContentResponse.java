package com.newsletter.model.response;

import com.newsletter.model.dao.Content;
import com.newsletter.model.dao.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentResponse extends Result implements Serializable {
    @Serial
    private static final long serialVersionUID = 1825631271681213872L;
    private Long id;
    private  String errorMessage;
    private List<Content> content;
    private LocalDateTime createdAt;
}
