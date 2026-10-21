package com.gym.model;

public class Member extends Person {

    private Long planId;

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    @Override
    public String getRole() {
        return "MEMBER";
    }

    @Override
    public void validate() {
        super.validate();
        check(planId != null, "planId is required");
    }
}
