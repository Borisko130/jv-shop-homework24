package com.internet.shop.exceptions;

public class HashingFailedException extends RuntimeException {
    public HashingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
