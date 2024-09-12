package com.example.authservice.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Custom Feign error decoder for handling errors from the user service.
 * <p>
 * This class implements the {@link ErrorDecoder} interface to provide custom error handling
 * for Feign client responses. It reads the response body, constructs a {@link UserServiceException}
 * with the response details, and returns it.
 * </p>
 */
public class UserServiceErrorDecoder implements ErrorDecoder {

    /**
     * Decodes the error response and constructs a {@link UserServiceException}.
     *
     * @param methodKey the Feign method key for the request that caused the error
     * @param response the Feign response containing error details
     * @return a {@link UserServiceException} with details from the response
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(response.body().asInputStream()))) {
            String body = input.lines().collect(Collectors.joining("\n"));
            return new UserServiceException(body, response.status());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
