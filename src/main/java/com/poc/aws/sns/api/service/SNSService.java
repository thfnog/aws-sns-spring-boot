package com.poc.aws.sns.api.service;

import com.amazonaws.services.sns.model.Subscription;
import com.poc.aws.sns.api.interfaces.json.Notification;
import com.poc.aws.sns.api.interfaces.json.request.TopicRequest;
import com.poc.aws.sns.api.interfaces.json.response.CreateTopicResponse;
import com.poc.aws.sns.api.interfaces.json.response.GetTopicResponse;
import java.util.List;

public interface SNSService {

  String subscribe(String topic);

  List<Subscription> getSubscribes(String topic);

  CreateTopicResponse createTopic(TopicRequest topicRequest);

  List<GetTopicResponse> getTopics();

  void publish(Notification notification, String topic);
}
