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
public class ContentResponse extends Result implements Serializable {
    @Serial
    private static final long serialVersionUID = 1825631271681213872L;
    private Long id;
    private String errorMessage;
    private Object data;
    private LocalDateTime createdAt;
}
