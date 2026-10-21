package com.gym.model;

public class Instructor extends Person {

    private String specialty;

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String getRole() {
        return "INSTRUCTOR";
    }

    @Override
    public void validate() {
        super.validate();
        check(hasText(specialty), "specialty is required");
    }
}
