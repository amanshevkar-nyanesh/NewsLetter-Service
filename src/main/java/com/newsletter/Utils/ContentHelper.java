package com.newsletter.Utils;

import com.newsletter.model.dao.Content;
import com.newsletter.model.response.ContentResponse;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.newsletter.enums.ResultCode.*;

@Component
public class ContentHelper {

    public ContentResponse getContentCreatedResponse(Content content, boolean isSuccessfullyProcessed) {
        ContentResponse contentResponse = new ContentResponse();
        if (isSuccessfullyProcessed && Objects.nonNull(content)) {
            contentResponse.setResultCode(CREATED.getCode());
            contentResponse.setResultStatus(CREATED.getStatus());
            contentResponse.setResultMessage(CREATED.getMessage());
            contentResponse.setId(content.getId());
            contentResponse.setCreatedAt(content.getCreatedAt());
        } else {
            contentResponse = getFailureResultCode(contentResponse);
        }
        return contentResponse;
    }

    public ContentResponse getContentsResponse(Object contents, boolean isIdBasedSearch) {
        ContentResponse contentResponse = new ContentResponse();
        if (Objects.isNull(contents) && isIdBasedSearch) {
            contentResponse.setResultCode(DATA_NOT_FOUND.getCode());
            contentResponse.setResultStatus(DATA_NOT_FOUND.getStatus());
            contentResponse.setResultMessage(DATA_NOT_FOUND.getMessage());
        }
        contentResponse = getSuccessResultCode(contentResponse);
        contentResponse.setData(contents);
        return contentResponse;
    }

    public ContentResponse getSuccessResultCode(ContentResponse contentResponse) {
        contentResponse.setResultCode(SUCCESS.getCode());
        contentResponse.setResultStatus(SUCCESS.getCode());
        contentResponse.setResultMessage(SUCCESS.getMessage());
        return contentResponse;
    }

    public ContentResponse getFailureResultCode(ContentResponse contentResponse) {
        contentResponse.setResultCode(FAILURE.getCode());
        contentResponse.setResultStatus(FAILURE.getCode());
        contentResponse.setResultMessage(FAILURE.getMessage());
        return contentResponse;
    }

    public ContentResponse getDeleteContentResponse(boolean isSuccessfullyProcessed) {
        ContentResponse contentResponse = new ContentResponse();
        if (isSuccessfullyProcessed) {
            contentResponse.setResultCode(DELETED.getCode());
            contentResponse.setResultStatus(DELETED.getCode());
            contentResponse.setResultMessage(DELETED.getMessage());
        } else {
            contentResponse = getFailureResultCode(contentResponse);
        }
        return contentResponse;
    }
}
