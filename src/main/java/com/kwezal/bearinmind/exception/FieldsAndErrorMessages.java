package com.kwezal.bearinmind.exception;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

record FieldsAndErrorMessages(Set<String> fields, List<String> errorMessages) {
    static FieldsAndErrorMessages fromBindException(BindException e) {
        final var fields = new HashSet<String>();
        final var errorMessages = new ArrayList<String>();

        for (final var error : e.getAllErrors()) {
            errorMessages.add(error.getDefaultMessage());
            if (error instanceof FieldError fieldError) {
                fields.add(fieldError.getField());
            }
        }

        return new FieldsAndErrorMessages(fields, errorMessages);
    }
}
