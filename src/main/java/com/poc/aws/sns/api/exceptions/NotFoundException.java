package com.poc.aws.sns.api.exceptions;

import com.poc.aws.sns.api.exceptions.MessageError.ApiError;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class NotFoundException extends RuntimeException {

  private static final long serialVersionUID = -6825521530842244140L;

  private final ApiError error;

  public NotFoundException(ApiError error) {
    super(error.toString());
    this.error = error;
  }

  public NotFoundException(ApiError error, String detail) {
    super(String.format("%s - Detail: %s", error.toString(), detail));
    this.error = error;
  }

}
