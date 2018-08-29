package com.kozanoglu.adtech.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DeliveryNotFoundException.class)
    public void handleDeliveryNotFound() { }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ClickNotFoundException.class)
    public void handleClickNotFound() { }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(Exception e) {
        LOG.error("Caught an exception", e);
    }
}