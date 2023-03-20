package com.kwezal.bearinmind.exception;

import com.kwezal.bearinmind.exception.response.ErrorCode;
import java.util.Map;

public class ForbiddenException extends AbstractException {

    public ForbiddenException(final String objectName, final Map<String, Object> properties, final String errorCode) {
        super(objectName, properties, errorCode);
    }

    public ForbiddenException(final String objectName, final Map<String, Object> properties) {
        super(objectName, properties, ErrorCode.FORBIDDEN);
    }

    public ForbiddenException(final String objectName) {
        super(objectName, null, ErrorCode.FORBIDDEN);
    }

    public ForbiddenException(final Class<?> objectClass, final Map<String, Object> properties, final String errorCode) {
        super(objectClass, properties, errorCode);
    }

    public ForbiddenException(final Class<?> objectClass, final Map<String, Object> properties) {
        super(objectClass, properties, ErrorCode.FORBIDDEN);
    }

    public ForbiddenException(final Class<?> objectClass) {
        super(objectClass, null, ErrorCode.FORBIDDEN);
    }
}
