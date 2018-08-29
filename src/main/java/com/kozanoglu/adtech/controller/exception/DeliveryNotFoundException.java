package com.kozanoglu.adtech.controller.exception;

import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

public class DeliveryNotFoundException extends RuntimeException {

    private JpaObjectRetrievalFailureException e;

    public DeliveryNotFoundException(JpaObjectRetrievalFailureException e) {
        this.e = e;
    }
}
