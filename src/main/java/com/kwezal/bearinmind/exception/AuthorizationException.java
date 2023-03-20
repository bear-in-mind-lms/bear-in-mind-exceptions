package com.kwezal.bearinmind.exception;

import com.kwezal.bearinmind.exception.response.ErrorCode;
import java.util.Map;

public class AuthorizationException extends AbstractException {

    public AuthorizationException(final String objectName, final Map<String, Object> properties, final String errorCode) {
        super(objectName, properties, errorCode);
    }

    public AuthorizationException(final String objectName, final Map<String, Object> properties) {
        super(objectName, properties, ErrorCode.AUTHORIZATION_ERROR);
    }

    public AuthorizationException(final String objectName) {
        super(objectName, null, ErrorCode.AUTHORIZATION_ERROR);
    }

    public AuthorizationException(final Class<?> objectClass, final Map<String, Object> properties, final String errorCode) {
        super(objectClass, properties, errorCode);
    }

    public AuthorizationException(final Class<?> objectClass, final Map<String, Object> properties) {
        super(objectClass, properties, ErrorCode.AUTHORIZATION_ERROR);
    }

    public AuthorizationException(final Class<?> objectClass) {
        super(objectClass, null, ErrorCode.AUTHORIZATION_ERROR);
    }
}
