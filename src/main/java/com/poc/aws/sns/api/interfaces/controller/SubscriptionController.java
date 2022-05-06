package com.poc.aws.sns.api.interfaces.controller;

import com.amazonaws.services.sns.model.Subscription;
import com.poc.aws.sns.api.service.SNSService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationMessage;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationSubject;
import org.springframework.cloud.aws.messaging.endpoint.NotificationStatus;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationMessageMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationSubscriptionMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationUnsubscribeConfirmationMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topic-subscriber")
@Slf4j
@RequiredArgsConstructor
public class SubscriptionController {

  private final SNSService snsService;

  @PostMapping("/subscribes/{topic}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Void> subscribe(@PathVariable String topic) {
    String requestId = snsService.subscribe(topic);
    return ResponseEntity
        .ok()
        .header("request-id", requestId)
        .build();
  }

  @GetMapping("/subscribes/{topic}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<List<Subscription>> getSubscribes(@PathVariable String topic) {
    List<Subscription> subscribes = snsService.getSubscribes(topic);
    return ResponseEntity.ok(subscribes);
  }

  @NotificationMessageMapping
  public void message(@NotificationMessage String message, @NotificationSubject String subject) {
    log.info("Received message: {}, having subject: {}", message, subject);
  }

  @NotificationSubscriptionMapping
  public void confirmSubscription(NotificationStatus notificationStatus) {
    notificationStatus.confirmSubscription();
    log.info("Confirmation SNS Topic subscription.");
  }

  @NotificationUnsubscribeConfirmationMapping
  public void confirmSubscriptionMessage(NotificationStatus notificationStatus) {
    notificationStatus.confirmSubscription();
    log.info("Unsubscribed from Topic.");
  }

}










