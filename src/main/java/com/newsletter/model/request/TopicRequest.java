package com.newsletter.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 6840835181759677195L;

    @NotBlank(message = "Topic name is required")
    private String name;

    private String description;
}
