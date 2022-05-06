package com.poc.aws.sns.api.interfaces.controller;

import com.poc.aws.sns.api.interfaces.json.Notification;
import com.poc.aws.sns.api.interfaces.json.request.TopicRequest;
import com.poc.aws.sns.api.interfaces.json.response.CreateTopicResponse;
import com.poc.aws.sns.api.interfaces.json.response.GetTopicResponse;
import com.poc.aws.sns.api.service.SNSService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
public class TopicController {

  private final SNSService SNSService;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<CreateTopicResponse> createTopic(@RequestBody TopicRequest topicRequest) {
    CreateTopicResponse createTopicResponse = SNSService.createTopic(topicRequest);
    return ResponseEntity
        .status(createTopicResponse.getStatus())
        .body(createTopicResponse);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<List<GetTopicResponse>> getTopics() {
    List<GetTopicResponse> topicsResponse = SNSService.getTopics();
    return ResponseEntity.ok(topicsResponse);
  }

  @PostMapping("/message")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void publishNotification(@RequestBody Notification notification) {
    SNSService.publish(notification);
  }

}










