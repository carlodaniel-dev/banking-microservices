package com.carlos.banking.account.exception;

public class ClienteSinCuentasException extends RuntimeException {

    public ClienteSinCuentasException(String message) {
        super(message);
    }
}