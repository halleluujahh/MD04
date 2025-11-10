package org.com.session06.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizeException extends Exception {
    public UnauthorizeException(String message) {
        super(message);
    }
}
