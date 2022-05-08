package com.poc.aws.sns.api.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSAppConfig {

  public static final String LOCAL_ENV = "local";
  public static final String LOCALSTASK_ENDPOINT = "http://0.0.0.0:4566";

  @Value("${aws.env}")
  public String env;

  @Value("${aws.sns.region}")
  public String snsRegion;

  @Value("${aws.sns.arn}")
  public String snsArn;

  @Value("${aws.sns.notification.topic-default}")
  public String topicDefault;

  @Value("${aws.sns.notification.protocol}")
  public String protocol;

  @Value("${aws.sns.notification.endpoint}")
  public String snsNotificationEndpoint;

  @Bean
  public String snsArn() {
    return this.snsArn;
  }

  @Bean
  public String protocol() {
    return this.protocol;
  }

  @Bean
  public String topicDefault() {
    return this.topicDefault;
  }

  @Bean
  public String snsNotificationEndpoint() {
    return this.snsNotificationEndpoint;
  }

  @Bean
  public AmazonSNS amazonSNS() {

    AmazonSNSClientBuilder amazonSNSClientBuilder = AmazonSNSClientBuilder
        .standard()
        .withCredentials(new DefaultAWSCredentialsProviderChain());

    if (env.equalsIgnoreCase(LOCAL_ENV)) {
      amazonSNSClientBuilder
          .withEndpointConfiguration(new EndpointConfiguration(LOCALSTASK_ENDPOINT, snsRegion));
    } else {
      amazonSNSClientBuilder.withRegion(snsRegion);
    }

    return amazonSNSClientBuilder.build();
  }

  @Bean
  public NotificationMessagingTemplate notificationMessagingTemplate(AmazonSNS amazonSNS) {
    return new NotificationMessagingTemplate(amazonSNS);
  }

}


