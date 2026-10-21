package com.gym.model;

public class Equipment extends BaseEntity {

    private String name;
    private Integer quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public void validate() {
        check(hasText(name), "name is required");
        check(quantity != null && quantity >= 0, "quantity must be zero or positive");
    }
}
