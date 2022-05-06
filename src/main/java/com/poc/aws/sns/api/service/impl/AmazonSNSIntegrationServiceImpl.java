package com.poc.aws.sns.api.service.impl;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.ListSubscriptionsByTopicResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.SubscribeResult;
import com.amazonaws.services.sns.model.Subscription;
import com.poc.aws.sns.api.exceptions.MessageError;
import com.poc.aws.sns.api.exceptions.UnprocessableEntityException;
import com.poc.aws.sns.api.interfaces.Messages;
import com.poc.aws.sns.api.interfaces.json.Notification;
import com.poc.aws.sns.api.interfaces.json.request.TopicRequest;
import com.poc.aws.sns.api.interfaces.json.response.CreateTopicResponse;
import com.poc.aws.sns.api.interfaces.json.response.GetTopicResponse;
import com.poc.aws.sns.api.service.SNSService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AmazonSNSIntegrationServiceImpl implements SNSService {

  private static final String HTTPS = "https";
  private static final String TOPIC_DEFAULT = "topic-subscriber";

  private final AmazonSNS amazonSNS;
  private final MessageError messageError;

  private final String snsArn;
  private final String snsNotificationEndpoint;

  /*
    Different ways to run method after startup in spring boot
      - https://stacktraceguru.com/springboot/run-method-on-startup
   */
  @PostConstruct
  private void subscribe() {
    subscribe(TOPIC_DEFAULT);
  }

  @Override
  public String subscribe(String topic) {
    SubscribeRequest request = new SubscribeRequest();
    request.setProtocol(HTTPS);
    request.setEndpoint(snsNotificationEndpoint);
    request.setReturnSubscriptionArn(true);

    String topicArn = snsArn + ":" + topic;
    request.setTopicArn(topicArn);

    try {
      SubscribeResult result = amazonSNS.subscribe(request);
      String requestId = result.getSdkResponseMetadata().getRequestId();
      log.info(
          "Subscription ARN is {}. Status is {}. RequestId: {}", result.getSubscriptionArn(), result
              .getSdkHttpMetadata().getHttpStatusCode(), requestId);
      return requestId;
    } catch (Exception e) {
      throw new UnprocessableEntityException(
          messageError.create(Messages.SNS_INTEGRATION_HAS_FAILED), e.getMessage());
    }
  }

  @Override
  public void publish(Notification notification) {
    try {
      String topicArn = snsArn + ":" + notification.getTopic();
      PublishRequest publishRequest = new PublishRequest(
          topicArn, notification.getBody(),
          notification.getSubject());
      PublishResult publishResult = this.amazonSNS.publish(publishRequest);

      log.info("MessageId: {}", publishResult.getMessageId());
    } catch (Exception e) {
      log.error("Not possible to publish notification.", e.getCause());
      throw new UnprocessableEntityException(
          messageError.create(Messages.SNS_INTEGRATION_HAS_FAILED, e.getMessage()));
    }
  }

  @Override
  public List<Subscription> getSubscribes(String topic) {
    try {
      ListSubscriptionsByTopicResult result = amazonSNS.listSubscriptionsByTopic(snsArn + ":" + topic);
      return Optional.ofNullable(result)
          .map(ListSubscriptionsByTopicResult::getSubscriptions)
          .orElse(List.of());
    } catch (Exception e) {
      log.error("Error to get subscribes", e.getCause());
    }

    return List.of();
  }

  @Override
  public CreateTopicResponse createTopic(TopicRequest topicRequest) {
    CreateTopicResult topicResult = amazonSNS.createTopic(topicRequest.getName());
    return CreateTopicResponse.builder()
        .arn(topicResult.getTopicArn())
        .status(topicResult
            .getSdkHttpMetadata()
            .getHttpStatusCode())
        .build();
  }

  @Override
  public List<GetTopicResponse> getTopics() {
    return amazonSNS.listTopics()
        .getTopics()
        .stream()
        .map(topic -> GetTopicResponse.builder()
            .arn(topic.getTopicArn())
            .build())
        .collect(Collectors.toList());
  }

}
