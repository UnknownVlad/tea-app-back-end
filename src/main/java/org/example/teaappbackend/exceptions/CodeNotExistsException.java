package org.example.teaappbackend.exceptions;

public class CodeNotExistsException extends RuntimeException{
    public CodeNotExistsException(String message) {
        super(message);
    }
}
