package com.kozanoglu.adtech.controller.exception;

import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

public class ClickNotFoundException extends RuntimeException {

    private JpaObjectRetrievalFailureException e;

    public ClickNotFoundException(JpaObjectRetrievalFailureException e) {
        this.e = e;
    }
}
