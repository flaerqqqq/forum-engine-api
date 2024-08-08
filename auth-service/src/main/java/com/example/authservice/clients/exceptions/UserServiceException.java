package com.example.authservice.clients.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserServiceException extends RuntimeException {

    private int status;

    public UserServiceException(String msg, int status) {
        super(msg);
        this.status = status;
    }
}
