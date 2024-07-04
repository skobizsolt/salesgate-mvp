package com.upscale.salesgate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(ServiceExpection.class)
    public ResponseEntity<Object> handleServiceException(final ServiceExpection exception) {
        final Map<String, Object> response = new ConcurrentHashMap<>();
        response.put("errorCode", String.format("SG_%03d", exception.getErrorCode()));
        response.put("message", exception.getMessage());
        response.put("timestamp:", Errors.getByCode(exception.getErrorCode()).getTimeStamp());
        return new ResponseEntity<>(response, exception.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleTransactionSystemException(final Exception exception) {
        return handleServiceException(new ServiceExpection(Errors.UNEXPECTED_ERROR, exception.getMessage()));
    }
}
