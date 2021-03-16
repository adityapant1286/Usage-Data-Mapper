package com.zuora.usagedatamapper.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ApiError {

    private int httpCode;
    private String httpStatus;
    private String message;
    private List<String> errors;

}
