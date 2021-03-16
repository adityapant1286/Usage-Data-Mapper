package com.zuora.usagedatamapper.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InstanceConfigRuntimeException extends BaseRuntimeException {
    public InstanceConfigRuntimeException(String message) {
        super(message);
    }
}
