package com.gym.exception;

public abstract class ApiException extends RuntimeException {

    protected ApiException(String message) {
        super(message);
    }

    public abstract int getStatus();
}
