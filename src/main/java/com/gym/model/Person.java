package com.gym.model;

public abstract class Person extends BaseEntity {

    private String name;
    private String email;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public abstract String getRole();

    @Override
    public void validate() {
        check(hasText(name), "name is required");
        check(hasText(email) && email.contains("@"), "email is invalid");
    }
}
