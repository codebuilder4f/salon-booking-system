package com.mpb.salon.bookig.system.web.exception;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException() {
        super("Invalid password");
    }
}
