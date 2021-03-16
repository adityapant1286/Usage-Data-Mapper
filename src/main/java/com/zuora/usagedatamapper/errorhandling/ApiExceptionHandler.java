package com.zuora.usagedatamapper.errorhandling;

import com.zuora.usagedatamapper.errorhandling.exceptions.InstanceConfigRuntimeException;
import com.zuora.usagedatamapper.errorhandling.exceptions.MappingRuntimeException;
import com.zuora.usagedatamapper.errorhandling.exceptions.ScheduleRuntimeException;
import com.zuora.usagedatamapper.errorhandling.exceptions.IORealmRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    // 400
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request) {
        log.info(ex.getClass().getName());
        List<String> errors = ex.getBindingResult().getFieldErrors()
                                                    .stream()
                                                    .map(e -> e.getField() + ": " + e.getDefaultMessage())
                                                    .collect(Collectors.toList());
        errors.addAll(ex.getBindingResult().getGlobalErrors()
                                            .stream()
                                            .map(e -> e.getObjectName() + ": " + e.getDefaultMessage())
                                            .collect(Collectors.toList())
        );

        final ApiError apiError = ApiError.builder()
                                    .httpCode(HttpStatus.BAD_REQUEST.value())
                                    .httpStatus(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                    .message(errors.toString())
                                    .errors(errors)
                                    .build();

        return handleExceptionInternal(ex, apiError, headers, status, request);
    }
/*

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex,
                                                         final HttpHeaders headers, final HttpStatus status,
                                                         final WebRequest request) {
        logger.info(ex.getClass().getName());

        List<String> errors = ex.getBindingResult().getFieldErrors()
                                                    .stream()
                                                    .map(e -> e.getField() + ": " + e.getDefaultMessage())
                                                    .collect(Collectors.toList());
        errors.addAll(ex.getBindingResult().getGlobalErrors()
                .stream()
                .map(e -> e.getObjectName() + ": " + e.getDefaultMessage())
                .collect(Collectors.toList())
        );
        final ApiError apiError = ApiError.builder()
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .message(ex.getLocalizedMessage())
                            .errors(errors)
                            .build();

        return handleExceptionInternal(ex, apiError, headers, apiError.getHttpStatus(), request);
    }
*/

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex,
                                                        final HttpHeaders headers, final HttpStatus status,
                                                        final WebRequest request) {
        log.info(ex.getClass().getName());
        //

        String error = ex.getValue() + " value for " +
                ex.getPropertyName() + " should be of type " +
                ex.getRequiredType();

        final ApiError apiError = ApiError.builder()
                .httpCode(HttpStatus.BAD_REQUEST.value())
                .httpStatus(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .errors(List.of(error))
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
                                                                     final HttpHeaders headers, final HttpStatus status,
                                                                     final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final String error = ex.getRequestPartName() + " part is missing";
        final ApiError apiError = ApiError.builder()
                .httpCode(HttpStatus.BAD_REQUEST.value())
                .httpStatus(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .errors(List.of(error))
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex,
                                                                          final HttpHeaders headers, final HttpStatus status,
                                                                          final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final String error = ex.getParameterName() + " parameter is missing";
        final ApiError apiError = ApiError.builder()
                .httpCode(HttpStatus.BAD_REQUEST.value())
                .httpStatus(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .errors(List.of(error))
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    //

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
                                                                   final WebRequest request) {
        log.debug(request.getParameterMap().toString());
        //
        final String error = ex.getName()
                + " should be of type "
                + Objects.requireNonNull(ex.getRequiredType()).getName();
        final ApiError apiError = ApiError.builder()
                .httpCode(HttpStatus.BAD_REQUEST.value())
                .httpStatus(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .errors(List.of(error))
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class,
                        InstanceConfigRuntimeException.class,
                        ScheduleRuntimeException.class,
                        IORealmRuntimeException.class,
                        MappingRuntimeException.class})
    public ResponseEntity<Object> handleBaseRuntimeExceptions(final RuntimeException ex,
                                                                   final WebRequest request) {
        log.debug(request.getParameterMap().toString());

        final ApiError apiError = ApiError.builder()
                .httpCode(HttpStatus.BAD_REQUEST.value())
                .httpStatus(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .errors(List.of(ex.getMessage()))
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
                                                            final WebRequest request) {
        log.debug(request.getParameterMap().toString());
        //

        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getRootBeanClass().getName() + " " + v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.toList());

        final ApiError apiError = ApiError.builder()
                .httpCode(HttpStatus.BAD_REQUEST.value())
                .httpStatus(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .errors(errors)
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // 404

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
                                                                   final HttpHeaders headers, final HttpStatus status,
                                                                   final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        final ApiError apiError = ApiError.builder()
                .httpCode(HttpStatus.NOT_FOUND.value())
                .httpStatus(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .errors(List.of(error))
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    // 405

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex,
                                                                         final HttpHeaders headers, final HttpStatus status,
                                                                         final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
//        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        List<String> errors = Objects.requireNonNull(ex.getSupportedHttpMethods())
                                .stream()
                                .map(Enum::toString)
                                .collect(Collectors.toList());
        builder.append(String.join(" ", errors));

        final ApiError apiError = ApiError.builder()
                .httpCode(HttpStatus.METHOD_NOT_ALLOWED.value())
                .httpStatus(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .errors(List.of(builder.toString()))
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    // 415

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
                                                                     final HttpHeaders headers, final HttpStatus status,
                                                                     final WebRequest request) {
        log.info(ex.getClass().getName());
        //
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");

        List<String> errors = ex.getSupportedMediaTypes()
                .stream()
                .map(MimeType::toString)
                .collect(Collectors.toList());
        builder.append(String.join(" ", errors));

        final ApiError apiError = ApiError.builder()
                .httpCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .httpStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .errors(List.of(builder.substring(0, builder.length() - 2)))
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    // 500

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        log.debug(request.getParameterMap().toString());
        log.error("error", ex);
        //
        final ApiError apiError = ApiError.builder()
                .httpCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .errors(List.of("Internal error occurred. Please contact System Administrator"))
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
