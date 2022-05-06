package com.poc.aws.sns.api.interfaces.json.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTopicResponse {

  private String arn;
  private int status;

}
