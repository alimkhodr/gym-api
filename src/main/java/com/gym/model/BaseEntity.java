package com.gym.model;

import com.gym.exception.BadRequestException;

public abstract class BaseEntity {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public abstract void validate();

    protected void check(boolean condition, String message) {
        if (!condition) {
            throw new BadRequestException(message);
        }
    }

    protected boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
