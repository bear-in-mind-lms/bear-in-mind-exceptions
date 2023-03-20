package com.kwezal.bearinmind.exception;

import static java.util.Objects.nonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwezal.bearinmind.exception.response.ErrorCode;
import com.kwezal.bearinmind.exception.response.ErrorResponse;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    @Value("${spring.application.name}")
    private String applicationName;

    private final ObjectMapper objectMapper;

    // NOT FOUND

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException e) {
        log.info(LogMessage.RESOURCE_NOT_FOUND, e.getObjectName(), e.getProperties());

        return new ErrorResponse(e.getErrorCode(), e.getErrorArguments(), applicationName);
    }

    // BAD REQUEST

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpClientErrorException.class)
    public ErrorResponse handleHttpClientErrorException(HttpClientErrorException e) {
        return handleRestClientResponseException(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestDataException.class)
    public ErrorResponse handleInvalidRequestDataException(InvalidRequestDataException e) {
        log.info(LogMessage.INVALID_REQUEST_DATA, e.getObjectName(), e.getProperties());

        return new ErrorResponse(e.getErrorCode(), e.getErrorArguments(), applicationName);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        { HttpMessageNotReadableException.class, IllegalArgumentException.class, ConstraintViolationException.class }
    )
    public ErrorResponse handleIllegalArgumentException(Exception e) {
        final var exceptionClassName = e.getClass().getSimpleName();
        log.error(LogMessage.GENERIC_EXCEPTION_MESSAGE, exceptionClassName, e.getMessage());

        return new ErrorResponse(ErrorCode.REQUEST_ARGUMENT_INVALID, Set.of(exceptionClassName), applicationName);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final var parameter = e.getParameter();
        final var parameterName = nonNull(parameter.getParameter().getName()) ? parameter.getParameter().getName() : "";
        final var method = parameter.getMethod();
        final var methodName = nonNull(method) ? method.getName() : "";

        final var fieldsErrorMessages = FieldsAndErrorMessages.fromBindException(e);
        final var fields = fieldsErrorMessages.fields();
        final var errorMessages = fieldsErrorMessages.errorMessages();

        log.info(LogMessage.METHOD_ARGUMENT_NOT_VALID_EXCEPTION, methodName, parameterName, fields, errorMessages);

        return new ErrorResponse(ErrorCode.REQUEST_ARGUMENT_INVALID, fields, applicationName);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResponse handleBindException(BindException e) {
        final var fieldsErrorMessages = FieldsAndErrorMessages.fromBindException(e);
        final var fields = fieldsErrorMessages.fields();
        final var errorMessages = fieldsErrorMessages.errorMessages();

        log.info(LogMessage.BIND_EXCEPTION, e.getObjectName(), fields, errorMessages);

        return new ErrorResponse(ErrorCode.REQUEST_ARGUMENT_INVALID, fields, applicationName);
    }

    // UNAUTHORIZED

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthorizationException.class)
    public ErrorResponse handleAuthorizationException(AuthorizationException e) {
        log.info(LogMessage.AUTHORIZATION_EXCEPTION, e.getObjectName(), e.getProperties());

        return new ErrorResponse(e.getErrorCode(), e.getErrorArguments(), applicationName);
    }

    // FORBIDDEN

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ErrorResponse handleForbiddenException(ForbiddenException e) {
        log.info(LogMessage.AUTHORIZATION_EXCEPTION, e.getObjectName(), e.getProperties());

        return new ErrorResponse(e.getErrorCode(), e.getErrorArguments(), applicationName);
    }

    // INTERNAL SERVER ERROR

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ HttpServerErrorException.class, UnknownHttpStatusCodeException.class })
    public ErrorResponse handleHttpServerErrorException(RestClientResponseException e) {
        return handleRestClientResponseException(e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ AccessDeniedException.class, SecurityException.class })
    public ErrorResponse handleSecurityExceptions(Exception e) {
        final var exceptionClassName = e.getClass().getSimpleName();
        log.error(LogMessage.GENERIC_EXCEPTION_MESSAGE, exceptionClassName, e.getMessage());

        return new ErrorResponse(ErrorCode.INTERNAL_SECURITY_ERROR, Set.of(exceptionClassName), applicationName);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(
        {
            EntityNotFoundException.class,
            IndexOutOfBoundsException.class,
            NoSuchElementException.class,
            NonUniqueResultException.class,
            NullPointerException.class,
            RollbackException.class,
        }
    )
    public ErrorResponse handleInternalServerErrors(Exception e) {
        final var exceptionClassName = e.getClass().getSimpleName();
        log.error(LogMessage.GENERIC_EXCEPTION_MESSAGE, exceptionClassName, e.getMessage());

        return new ErrorResponse(ErrorCode.INTERNAL_ERROR, Set.of(exceptionClassName), applicationName);
    }

    private ErrorResponse handleRestClientResponseException(RestClientResponseException e) {
        final var bodyAsString = e.getResponseBodyAsString();

        try {
            return objectMapper.readValue(bodyAsString, ErrorResponse.class);
        } catch (JsonProcessingException jpe) {
            log.error(LogMessage.GENERIC_EXCEPTION_MESSAGE, e.getClass().getSimpleName(), e.getMessage());
            return new ErrorResponse(ErrorCode.CONNECTION_ERROR, null, applicationName);
        }
    }
}
