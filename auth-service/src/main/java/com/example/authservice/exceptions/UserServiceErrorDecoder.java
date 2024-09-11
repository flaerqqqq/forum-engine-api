package com.example.authservice.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class UserServiceErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(response.body().asInputStream()))) {
            String body = input.lines().collect(Collectors.joining("\n"));

            return new UserServiceException(body, response.status());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
