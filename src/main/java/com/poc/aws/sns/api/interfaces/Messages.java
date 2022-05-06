package com.poc.aws.sns.api.interfaces;

public final class Messages {

  // 400
  public static final String FIELD_VALIDATION = "400.001";
  public static final String JSON_VALIDATION = "400.002";
  public static final String REQUIRED_PARAM = "400.003";
  public static final String INVALID_PARAM = "400.004";
  public static final String REQUIRED_REQUEST_BODY = "400.005";
  public static final String INVALID_FIELD = "400.006";
  public static final String REQUIRED_HEADER = "400.007";

  // 422
  public static final String CONTACT_SYSTEM_ADMIN = "422.001";
  public static final String SNS_INTEGRATION_HAS_FAILED = "422.002";
}
