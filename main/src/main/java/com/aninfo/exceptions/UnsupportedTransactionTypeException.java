package com.aninfo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UnsupportedTransactionTypeException extends RuntimeException {

    public UnsupportedTransactionTypeException(String message) {
        super(message);
    }
}
