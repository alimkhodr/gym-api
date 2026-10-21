package com.gym.model;

import java.math.BigDecimal;

public class Plan extends BaseEntity {

    private String name;
    private BigDecimal monthlyPrice;
    private Integer durationMonths;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(BigDecimal monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public Integer getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(Integer durationMonths) {
        this.durationMonths = durationMonths;
    }

    @Override
    public void validate() {
        check(hasText(name), "name is required");
        check(monthlyPrice != null && monthlyPrice.signum() >= 0, "monthlyPrice must be zero or positive");
        check(durationMonths != null && durationMonths > 0, "durationMonths must be positive");
    }
}
