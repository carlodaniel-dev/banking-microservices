package com.carlos.banking.account.exception;

public class CuentaAlreadyExistsException extends RuntimeException {

    public CuentaAlreadyExistsException(String message) {
        super(message);
    }
}