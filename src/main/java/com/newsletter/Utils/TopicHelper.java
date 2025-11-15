package com.newsletter.Utils;

import com.newsletter.model.dao.Topic;
import com.newsletter.model.response.TopicResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.newsletter.enums.ResultCode.*;

@Component
public class TopicHelper {

    public TopicResponse createTopicResponse(Topic topic, boolean isSuccessfullyProcessed) {
        TopicResponse topicResponse = new TopicResponse();
        if (isSuccessfullyProcessed && Objects.nonNull(topic)) {
            topicResponse.setResultCode(CREATED.getCode());
            topicResponse.setResultStatus(CREATED.getStatus());
            topicResponse.setResultMessage(CREATED.getMessage());
            topicResponse.setId(topic.getId());
            topicResponse.setName(topic.getName());
            topicResponse.setCreatedAt(topic.getCreatedAt());
        } else {
            topicResponse = getFailureResultCode(topicResponse);
        }
        return topicResponse;
    }

    public TopicResponse getTopicResponse(Topic topic) {
        TopicResponse topicResponse = new TopicResponse();
        if (Objects.isNull(topic)) {
            topicResponse.setResultCode(DATA_NOT_FOUND.getCode());
            topicResponse.setResultStatus(DATA_NOT_FOUND.getStatus());
            topicResponse.setResultMessage(DATA_NOT_FOUND.getMessage());
            return topicResponse;
        }
        topicResponse = getSuccessResultCode(topicResponse);
        topicResponse.setId(topic.getId());
        topicResponse.setName(topic.getName());
        topicResponse.setCreatedAt(topic.getCreatedAt());
        topicResponse.setDescription(topic.getDescription());
        return topicResponse;
    }

    public TopicResponse getAllTopicsResponse(List<Topic> topics) {
        TopicResponse topicResponse = new TopicResponse();
        topicResponse = getSuccessResultCode(topicResponse);
        if (CollectionUtils.isEmpty(topics)) {
            topicResponse.setData(Collections.emptyList());
            return topicResponse;
        }
        topicResponse.setData(topics);
        return topicResponse;
    }

    public TopicResponse getSuccessResultCode(TopicResponse topicResponse) {
        topicResponse.setResultCode(SUCCESS.getCode());
        topicResponse.setResultStatus(SUCCESS.getCode());
        topicResponse.setResultMessage(SUCCESS.getMessage());
        return topicResponse;
    }

    public TopicResponse getFailureResultCode(TopicResponse topicResponse) {
        topicResponse.setResultCode(FAILURE.getCode());
        topicResponse.setResultStatus(FAILURE.getCode());
        topicResponse.setResultMessage(FAILURE.getMessage());
        return topicResponse;
    }

    public TopicResponse getDeleteTopicResponse(boolean isSuccessfullyProcessed) {
        TopicResponse topicResponse = new TopicResponse();
        if (isSuccessfullyProcessed) {
            topicResponse.setResultCode(DELETED.getCode());
            topicResponse.setResultStatus(DELETED.getCode());
            topicResponse.setResultMessage(DELETED.getMessage());
        } else {
            topicResponse = getFailureResultCode(topicResponse);
        }
        return topicResponse;
    }
}
