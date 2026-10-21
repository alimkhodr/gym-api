package com.gym.exception;

public class BadRequestException extends ApiException {

    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public int getStatus() {
        return 400;
    }
}
