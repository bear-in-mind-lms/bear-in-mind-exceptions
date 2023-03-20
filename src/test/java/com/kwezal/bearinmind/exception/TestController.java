package com.kwezal.bearinmind.exception;

import com.kwezal.bearinmind.exception.response.ErrorCode;
import com.kwezal.bearinmind.exception.response.ErrorResponse;
import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TestController {

    private static final String EXCEPTION_MESSAGE = "unexpectedly exceptional expected exception";

    private final TestService testService;

    @GetMapping("/faulty-communication")
    public void connectToFaultyApplication(@RequestParam int port, @RequestParam int code) {
        final var restTemplate = new RestTemplate();
        restTemplate.getForObject("http://localhost:{port}/faulty-application?code={code}", Object.class, port, code);
    }

    @GetMapping("/faulty-application")
    public ResponseEntity<Object> returnErrorResponseFromExternalApplication(@RequestParam int code) {
        return code == 400
            ? ResponseEntity
                .badRequest()
                .body(new ErrorResponse(ErrorCode.REQUEST_ARGUMENT_INVALID, null, "faulty-application"))
            : ResponseEntity
                .internalServerError()
                .body(new ErrorResponse(ErrorCode.INTERNAL_ERROR, null, "faulty-application"));
    }

    @GetMapping("/not-found")
    public void throwResourceNotFoundException() {
        throw new ResourceNotFoundException("ResourceNotFound", Map.of("exception", "ResourceNotFoundException"));
    }

    @GetMapping("/http-client-error")
    public void throwHttpClientErrorException() {
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, EXCEPTION_MESSAGE);
    }

    @GetMapping("/invalid-request-data")
    public void throwInvalidRequestDataException() {
        throw new InvalidRequestDataException("InvalidRequestData", Map.of("exception", "InvalidRequestDataException"));
    }

    @GetMapping("/http-message-not-readable")
    public void throwHttpMessageNotReadableException() {
        throw new HttpMessageNotReadableException(EXCEPTION_MESSAGE, testService.createHttpInputMessage());
    }

    @GetMapping("/illegal-argument")
    public void throwIllegalArgumentException() {
        throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }

    @GetMapping("/constraint-violation")
    public void throwConstraintViolationException() {
        throw new ConstraintViolationException(EXCEPTION_MESSAGE, null);
    }

    @GetMapping("/method-argument-not-valid")
    public void throwMethodArgumentNotValidException() throws MethodArgumentNotValidException, NoSuchMethodException {
        throw new MethodArgumentNotValidException(
            testService.createMethodParameter(),
            testService.createBindingResult("MethodArgumentNotValidException")
        );
    }

    @GetMapping("/bind-exception")
    public void throwBindException() throws BindException {
        throw new BindException(testService.createBindingResult("BindException"));
    }

    @GetMapping("/authorization-exception")
    public void throwAuthorizationException() {
        throw new AuthorizationException("Unauthorized", Map.of("exception", "AuthorizationException"));
    }

    @GetMapping("/forbidden")
    public void throwForbiddenException() {
        throw new ForbiddenException("Forbidden", Map.of("exception", "ForbiddenException"));
    }

    @GetMapping("/http-server-error")
    public void throwHttpServerErrorException() {
        throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, EXCEPTION_MESSAGE);
    }

    @GetMapping("/unknown-http-status-code")
    public void throwUnknownHttpStatusCodeException() {
        throw new UnknownHttpStatusCodeException(EXCEPTION_MESSAGE, 618, "I'm a coffeemaker", null, null, null);
    }

    @GetMapping("/access-denied")
    public void throwAccessDeniedException() throws AccessDeniedException {
        throw new AccessDeniedException("/etc/shadow");
    }

    @GetMapping("/security-exception")
    public void throwSecurityException() {
        throw new SecurityException(EXCEPTION_MESSAGE);
    }

    @GetMapping("/entity-not-found")
    public void throwEntityNotFoundException() {
        throw new EntityNotFoundException(EXCEPTION_MESSAGE);
    }

    @GetMapping("/index-out-of-bounds")
    public void throwIndexOutOfBoundsException() {
        throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
    }

    @GetMapping("/no-such-element")
    public void throwNoSuchElementException() {
        throw new NoSuchElementException(EXCEPTION_MESSAGE);
    }

    @GetMapping("/non-unique-result")
    public void throwNonUniqueResultException() {
        throw new NonUniqueResultException(EXCEPTION_MESSAGE);
    }

    @GetMapping("/null-pointer-exception")
    public void throwNullPointerException() {
        throw new NullPointerException(EXCEPTION_MESSAGE);
    }

    @GetMapping("/rollback-exception")
    public void throwRollbackException() {
        throw new RollbackException(EXCEPTION_MESSAGE);
    }
}
