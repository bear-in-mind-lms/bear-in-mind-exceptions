package com.kwezal.bearinmind.exception;

import com.kwezal.bearinmind.exception.response.ErrorCode;
import java.util.Map;
import lombok.Getter;

@Getter
public class InvalidRequestDataException extends AbstractException {

    public InvalidRequestDataException(final String objectName, final Map<String, Object> properties, final String errorCode) {
        super(objectName, properties, errorCode);
    }

    public InvalidRequestDataException(final String objectName, final Map<String, Object> properties) {
        super(objectName, properties, ErrorCode.REQUEST_ARGUMENT_INVALID);
    }

    public InvalidRequestDataException(final String objectName) {
        super(objectName, null, ErrorCode.REQUEST_ARGUMENT_INVALID);
    }

    public InvalidRequestDataException(
        final Class<?> objectClass,
        final Map<String, Object> properties,
        final String errorCode
    ) {
        super(objectClass, properties, errorCode);
    }

    public InvalidRequestDataException(final Class<?> objectClass, final Map<String, Object> properties) {
        super(objectClass, properties, ErrorCode.REQUEST_ARGUMENT_INVALID);
    }

    public InvalidRequestDataException(final Class<?> objectClass) {
        super(objectClass, null, ErrorCode.REQUEST_ARGUMENT_INVALID);
    }
}
