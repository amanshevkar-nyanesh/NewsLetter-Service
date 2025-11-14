package com.newsletter.model.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class Result implements Serializable {
    @Serial
    private static final long serialVersionUID = -8061007483974212463L;

    private String resultCode;
    private String resultStatus;
    private String resultMessage;
}
