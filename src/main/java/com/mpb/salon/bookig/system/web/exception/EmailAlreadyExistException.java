package com.mpb.salon.bookig.system.web.exception;

public class EmailAlreadyExistException extends RuntimeException{
    public EmailAlreadyExistException() {
        super("Email is already exists");
    }
}
