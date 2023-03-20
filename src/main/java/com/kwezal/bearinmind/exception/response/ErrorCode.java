package com.kwezal.bearinmind.exception.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorCode {

    // NOT FOUND
    public static final String NOT_FOUND = "NOT_FOUND";

    // BAD REQUEST
    public static final String REQUEST_ARGUMENT_INVALID = "REQUEST_ARGUMENT_INVALID";
    public static final String CONNECTION_ERROR = "CONNECTION_ERROR";

    // AUTHORIZATION
    public static final String AUTHORIZATION_ERROR = "AUTHORIZATION_ERROR";

    // FORBIDDEN
    public static final String FORBIDDEN = "FORBIDDEN";

    // INTERNAL SERVER ERROR
    public static final String INTERNAL_ERROR = "INTERNAL_ERROR";
    public static final String INTERNAL_SECURITY_ERROR = "INTERNAL_SECURITY_ERROR";
}
