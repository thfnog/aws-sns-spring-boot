package com.poc.aws.sns.api.exceptions;

import com.google.common.collect.Lists;
import com.poc.aws.sns.api.exceptions.MessageError.ApiError;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class UnprocessableEntityException extends RuntimeException {

  private static final long serialVersionUID = -5012441619995884439L;

  private final List<ApiError> errors;

  public UnprocessableEntityException(ApiError error) {
    this(Lists.newArrayList(error));
  }

  public UnprocessableEntityException(List<ApiError> errors) {
    super(errors.toString());
    this.errors = errors;
  }

  public UnprocessableEntityException(ApiError error, String detail) {
    super(String.format("%s - Detail: %s", error.toString(), detail));
    this.errors = Lists.newArrayList(error);
  }

}
