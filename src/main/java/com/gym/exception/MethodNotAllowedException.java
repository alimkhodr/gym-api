package com.gym.exception;

public class MethodNotAllowedException extends ApiException {

    public MethodNotAllowedException(String method) {
        super("method not allowed: " + method);
    }

    @Override
    public int getStatus() {
        return 405;
    }
}
