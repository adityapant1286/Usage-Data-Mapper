package com.zuora.usagedatamapper.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BaseRuntimeException extends RuntimeException {
    BaseRuntimeException(String message) { super(message); }
}
