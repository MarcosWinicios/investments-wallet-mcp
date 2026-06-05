package com.demo.investments_wallet.domain.exception;

public class DomainValidationException extends RuntimeException {

    public DomainValidationException(String message) {
        super(message);
    }
}
