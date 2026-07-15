package com.newsletter.Utils;

import com.newsletter.model.dao.Subscriber;
import com.newsletter.model.response.SubscriberResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.newsletter.enums.ResultCode.*;

@Component
public class SubscriberHelper {

    public SubscriberResponse createSubscriberResponse(Subscriber subscriber, boolean isSuccessfullyProcessed) {
        SubscriberResponse subscriberResponse = new SubscriberResponse();
        if (isSuccessfullyProcessed && Objects.nonNull(subscriber)) {
            subscriberResponse.setResultCode(CREATED.getCode());
            subscriberResponse.setResultStatus(CREATED.getStatus());
            subscriberResponse.setResultMessage(CREATED.getMessage());
            subscriberResponse.setId(subscriber.getId());
            subscriberResponse.setIsActive(subscriber.getIsActive());
            subscriberResponse.setCreatedAt(subscriber.getCreatedAt());
        } else {
            subscriberResponse = getFailureResultCode(subscriberResponse);
        }
        return subscriberResponse;
    }

    public SubscriberResponse getSubscriberResponse(Subscriber subscriber) {
        SubscriberResponse subscriberResponse = new SubscriberResponse();
        if (Objects.isNull(subscriber)) {
            subscriberResponse.setResultCode(DATA_NOT_FOUND.getCode());
            subscriberResponse.setResultStatus(DATA_NOT_FOUND.getStatus());
            subscriberResponse.setResultMessage(DATA_NOT_FOUND.getMessage());
            return subscriberResponse;
        }
        subscriberResponse = getSuccessResultCode(subscriberResponse);
        return getSubscriberBaseResponse(subscriber, subscriberResponse);
    }

    public SubscriberResponse getAllSubscribersResponse(List<Subscriber> subscribers) {
        SubscriberResponse subscriberResponse = new SubscriberResponse();
        subscriberResponse = getSuccessResultCode(subscriberResponse);
        if (CollectionUtils.isEmpty(subscribers)) {
            subscriberResponse.setData(Collections.emptyList());
            return subscriberResponse;
        }
        subscriberResponse.setData(subscribers);
        return subscriberResponse;
    }

    public SubscriberResponse getSuccessResultCode(SubscriberResponse subscriberResponse) {
        subscriberResponse.setResultCode(SUCCESS.getCode());
        subscriberResponse.setResultStatus(SUCCESS.getCode());
        subscriberResponse.setResultMessage(SUCCESS.getMessage());
        return subscriberResponse;
    }

    public SubscriberResponse getResourceUpdatedResultCode(SubscriberResponse subscriberResponse) {
        subscriberResponse.setResultCode(UPDATED.getCode());
        subscriberResponse.setResultStatus(UPDATED.getCode());
        subscriberResponse.setResultMessage(UPDATED.getMessage());
        return subscriberResponse;
    }

    public SubscriberResponse getFailureResultCode(SubscriberResponse subscriberResponse) {
        subscriberResponse.setResultCode(FAILURE.getCode());
        subscriberResponse.setResultStatus(FAILURE.getCode());
        subscriberResponse.setResultMessage(FAILURE.getMessage());
        return subscriberResponse;
    }

    public SubscriberResponse getDeactiveSubscriberResponse(boolean isSuccessfullyProcessed) {
        SubscriberResponse subscriberResponse = new SubscriberResponse();
        if (isSuccessfullyProcessed) {
            subscriberResponse.setResultCode(DELETED.getCode());
            subscriberResponse.setResultStatus(DELETED.getCode());
            subscriberResponse.setResultMessage(DELETED.getMessage());
        } else {
            subscriberResponse = getFailureResultCode(subscriberResponse);
        }
        return subscriberResponse;
    }

    public SubscriberResponse getSubscriberStatusResponse(Subscriber subscriber, boolean isSuccessfullyProcessed) {
        SubscriberResponse subscriberResponse = new SubscriberResponse();
        if (isSuccessfullyProcessed && Objects.nonNull(subscriber)) {
            subscriberResponse = getResourceUpdatedResultCode(subscriberResponse);
            return getSubscriberBaseResponse(subscriber, subscriberResponse);
        } else {
            subscriberResponse = getFailureResultCode(subscriberResponse);
        }
        return subscriberResponse;
    }

    public SubscriberResponse getSubscriberBaseResponse(Subscriber subscriber, SubscriberResponse subscriberResponse) {
        subscriberResponse.setId(subscriber.getId());
        subscriberResponse.setName(subscriber.getName());
        subscriberResponse.setEmail(subscriber.getEmail());
        subscriberResponse.setIsActive(subscriber.getIsActive());
        subscriberResponse.setCreatedAt(subscriber.getCreatedAt());
        subscriberResponse.setSubscribedTopics(subscriber.getSubscribedTopics());
        return subscriberResponse;
    }
}
