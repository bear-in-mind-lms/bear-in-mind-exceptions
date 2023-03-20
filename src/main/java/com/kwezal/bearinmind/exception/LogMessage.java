package com.kwezal.bearinmind.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class LogMessage {

    static final String GENERIC_EXCEPTION_MESSAGE = "'{}': '{}'";
    static final String RESOURCE_NOT_FOUND = "'{}' not found for arguments '{}'";
    static final String METHOD_ARGUMENT_NOT_VALID_EXCEPTION =
        "Arguments validation failed for method '{}' and parameter '{}': fields='{}', messages='{}'";
    static final String BIND_EXCEPTION = "Arguments validation failed for object '{}': fields='{}', messages='{}'";
    static final String AUTHORIZATION_EXCEPTION = "Authorization exception for object '{}' and arguments '{}'";
    static final String INVALID_REQUEST_DATA = "Invalid request data for object '{}' and arguments '{}'";
}
