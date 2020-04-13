package com.example.rickmorty.exceptions;

public class HttpDataException extends RuntimeException {
    public HttpDataException(String message) {
        super(message);
    }

    public HttpDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
