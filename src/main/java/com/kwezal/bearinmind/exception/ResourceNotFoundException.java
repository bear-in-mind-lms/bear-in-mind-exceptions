package com.kwezal.bearinmind.exception;

import com.kwezal.bearinmind.exception.response.ErrorCode;
import java.util.Map;

public class ResourceNotFoundException extends AbstractException {

    public ResourceNotFoundException(final String objectName, final Map<String, Object> properties, final String errorCode) {
        super(objectName, properties, errorCode);
    }

    public ResourceNotFoundException(final String objectName, final Map<String, Object> properties) {
        super(objectName, properties, ErrorCode.NOT_FOUND);
    }

    public ResourceNotFoundException(final String objectName) {
        super(objectName, null, ErrorCode.NOT_FOUND);
    }

    public ResourceNotFoundException(final Class<?> objectClass, final Map<String, Object> properties, final String errorCode) {
        super(objectClass, properties, errorCode);
    }

    public ResourceNotFoundException(final Class<?> objectClass, final Map<String, Object> properties) {
        super(objectClass, properties, ErrorCode.NOT_FOUND);
    }

    public ResourceNotFoundException(final Class<?> objectClass) {
        super(objectClass, null, ErrorCode.NOT_FOUND);
    }
}
