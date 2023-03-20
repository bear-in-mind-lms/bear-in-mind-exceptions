package com.kwezal.bearinmind.exception;

import java.io.InputStream;
import java.util.Collections;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public class TestService {

    public HttpInputMessage createHttpInputMessage() {
        return new MockHttpInputMessage(InputStream.nullInputStream());
    }

    public MethodParameter createMethodParameter() throws NoSuchMethodException {
        return new MethodParameter(
            ControllerExceptionHandler.class.getMethod(
                    "handleMethodArgumentNotValidException",
                    MethodArgumentNotValidException.class
                ),
            0
        );
    }

    public BindingResult createBindingResult(final String defaultMessage) {
        final var bindingResult = new MapBindingResult(Collections.emptyMap(), "BindingResult");
        bindingResult.addError(new FieldError("", "exception", defaultMessage));
        return bindingResult;
    }
}
