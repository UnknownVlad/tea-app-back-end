package org.example.teaappbackend.exceptions;

public class CodeTimeOutException extends RuntimeException {
    public CodeTimeOutException(String message) {
        super(message);
    }
}
