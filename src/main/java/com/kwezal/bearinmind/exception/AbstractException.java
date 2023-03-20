package com.kwezal.bearinmind.exception;

import static java.util.Objects.isNull;

import java.util.Map;
import java.util.Set;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public abstract class AbstractException extends RuntimeException {

    private final String objectName;

    @Nullable
    private final Map<String, Object> properties;

    private final String errorCode;

    protected AbstractException(final String objectName, final Map<String, Object> properties, final String errorCode) {
        super("object=" + objectName + ", properties=" + properties);
        this.objectName = objectName;
        this.properties = properties;
        this.errorCode = errorCode;
    }

    protected AbstractException(final Class<?> objectClass, final Map<String, Object> properties, final String errorCode) {
        this(objectClass.getSimpleName(), properties, errorCode);
    }

    public @Nullable Set<String> getErrorArguments() {
        return isNull(properties) ? null : properties.keySet();
    }
}
