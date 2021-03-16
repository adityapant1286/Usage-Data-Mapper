package com.zuora.usagedatamapper.errorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ScheduleRuntimeException extends BaseRuntimeException {
    public ScheduleRuntimeException(String message) {
        super(message);
    }
}
