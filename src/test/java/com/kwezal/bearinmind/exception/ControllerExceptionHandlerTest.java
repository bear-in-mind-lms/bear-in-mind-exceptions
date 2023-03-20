package com.kwezal.bearinmind.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.kwezal.bearinmind.exception.response.ErrorCode;
import com.kwezal.bearinmind.exception.response.ErrorResponse;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControllerExceptionHandlerTest {

    private static final String ORIGIN = "bear-in-mind-exceptions";

    @Autowired
    private ServletWebServerApplicationContext servletWebServerApplicationContext;

    @Autowired
    private WebTestClient webClient;

    @ParameterizedTest
    @MethodSource("Should_ReturnErrorResponseWithOrigin_When_ErrorResponseIsReturnedFromExternalApplication_Source")
    void Should_ReturnErrorResponseWithOrigin_When_ErrorResponseIsReturnedFromExternalApplication(
        HttpStatus expectedStatus,
        String expectedErrorCode
    ) {
        // GIVEN
        final var port = servletWebServerApplicationContext.getWebServer().getPort();

        final var expectedOrigin = "faulty-application";

        // WHEN
        final var response = webClient
            .get()
            .uri(builder ->
                builder
                    .path("/faulty-communication")
                    .queryParam("port", port)
                    .queryParam("code", expectedStatus.value())
                    .build()
            )
            .exchange();

        // THEN
        response.expectStatus().isEqualTo(expectedStatus);

        // AND
        response
            .expectBody(ErrorResponse.class)
            .value(responseDto -> {
                assertEquals(expectedErrorCode, responseDto.code());
                assertNull(responseDto.arguments());
                assertEquals(expectedOrigin, responseDto.origin());
            });
    }

    private static Stream<Arguments> Should_ReturnErrorResponseWithOrigin_When_ErrorResponseIsReturnedFromExternalApplication_Source() {
        return Stream.of(
            Arguments.of(HttpStatus.BAD_REQUEST, ErrorCode.REQUEST_ARGUMENT_INVALID),
            Arguments.of(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_ERROR)
        );
    }

    @ParameterizedTest
    @MethodSource("Should_ReturnErrorResponse_When_ExceptionIsThrown_Source")
    void Should_ReturnErrorResponse_When_ExceptionIsThrown(
        String url,
        HttpStatus expectedStatus,
        String expectedErrorCode,
        Set<String> expectedArguments
    ) {
        // WHEN
        final var response = webClient.get().uri(url).exchange();

        // THEN
        response.expectStatus().isEqualTo(expectedStatus);

        // AND
        response
            .expectBody(ErrorResponse.class)
            .value(responseDto -> {
                assertEquals(expectedErrorCode, responseDto.code());
                assertEquals(expectedArguments, responseDto.arguments());
                assertEquals(ORIGIN, responseDto.origin());
            });
    }

    private static Stream<Arguments> Should_ReturnErrorResponse_When_ExceptionIsThrown_Source() {
        return Stream.of(
            Arguments.of("/not-found", HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, Set.of("exception")),
            Arguments.of("/http-client-error", HttpStatus.BAD_REQUEST, ErrorCode.CONNECTION_ERROR, null),
            Arguments.of(
                "/invalid-request-data",
                HttpStatus.BAD_REQUEST,
                ErrorCode.REQUEST_ARGUMENT_INVALID,
                Set.of("exception")
            ),
            Arguments.of(
                "/http-message-not-readable",
                HttpStatus.BAD_REQUEST,
                ErrorCode.REQUEST_ARGUMENT_INVALID,
                Set.of("HttpMessageNotReadableException")
            ),
            Arguments.of(
                "/illegal-argument",
                HttpStatus.BAD_REQUEST,
                ErrorCode.REQUEST_ARGUMENT_INVALID,
                Set.of("IllegalArgumentException")
            ),
            Arguments.of(
                "/constraint-violation",
                HttpStatus.BAD_REQUEST,
                ErrorCode.REQUEST_ARGUMENT_INVALID,
                Set.of("ConstraintViolationException")
            ),
            Arguments.of(
                "/method-argument-not-valid",
                HttpStatus.BAD_REQUEST,
                ErrorCode.REQUEST_ARGUMENT_INVALID,
                Set.of("exception")
            ),
            Arguments.of("/bind-exception", HttpStatus.BAD_REQUEST, ErrorCode.REQUEST_ARGUMENT_INVALID, Set.of("exception")),
            Arguments.of(
                "/authorization-exception",
                HttpStatus.UNAUTHORIZED,
                ErrorCode.AUTHORIZATION_ERROR,
                Set.of("exception")
            ),
            Arguments.of("/forbidden", HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN, Set.of("exception")),
            Arguments.of("/http-server-error", HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.CONNECTION_ERROR, null),
            Arguments.of("/unknown-http-status-code", HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.CONNECTION_ERROR, null),
            Arguments.of(
                "/access-denied",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_SECURITY_ERROR,
                Set.of("AccessDeniedException")
            ),
            Arguments.of(
                "/security-exception",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_SECURITY_ERROR,
                Set.of("SecurityException")
            ),
            Arguments.of(
                "/entity-not-found",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_ERROR,
                Set.of("EntityNotFoundException")
            ),
            Arguments.of(
                "/index-out-of-bounds",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_ERROR,
                Set.of("IndexOutOfBoundsException")
            ),
            Arguments.of(
                "/no-such-element",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_ERROR,
                Set.of("NoSuchElementException")
            ),
            Arguments.of(
                "/non-unique-result",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_ERROR,
                Set.of("NonUniqueResultException")
            ),
            Arguments.of(
                "/null-pointer-exception",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_ERROR,
                Set.of("NullPointerException")
            ),
            Arguments.of(
                "/rollback-exception",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_ERROR,
                Set.of("RollbackException")
            )
        );
    }
}
