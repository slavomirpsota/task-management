package org.psota.taskmanagementbe.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(ServiceException.class)
    protected ResponseEntity<Object> handleServiceException(ServiceException e) {
        var message = getMessage(e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder().message(message).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TaskManagementException.class)
    protected ResponseEntity<Object> handleServiceException(TaskManagementException e) {
        var message = getMessage(e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder().message(message).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getMessage(String key) {
        return messageSource.getMessage(key, null, Locale.ROOT);
    }
}
