package com.poc.aws.sns.api.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSAppConfig {

  @Value("${aws.sns.region}")
  public String snsRegion;

  @Value("${aws.sns.arn}")
  public String snsArn;

  @Value("${aws.sns.topic-default}")
  public String topicDefault;

  @Value("${aws.sns.notification.endpoint}")
  public String snsNotificationEndpoint;

  @Bean
  public String snsArn() {
    return this.snsArn;
  }

  @Bean
  public String snsNotificationEndpoint() {
    return this.snsNotificationEndpoint;
  }

  @Bean
  public AmazonSNS amazonSNS() {
    return AmazonSNSClientBuilder
        .standard()
        .withRegion(snsRegion)
        .withCredentials(new DefaultAWSCredentialsProviderChain())
        .build();
  }

  @Bean
  public NotificationMessagingTemplate notificationMessagingTemplate(AmazonSNS amazonSNS) {
    return new NotificationMessagingTemplate(amazonSNS);
  }

}


