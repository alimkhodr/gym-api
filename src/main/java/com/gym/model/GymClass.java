package com.gym.model;

public class GymClass extends BaseEntity {

    private String name;
    private String schedule;
    private Integer capacity;
    private Long instructorId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public boolean isFull(int enrolled) {
        return enrolled >= capacity;
    }

    @Override
    public void validate() {
        check(hasText(name), "name is required");
        check(hasText(schedule), "schedule is required");
        check(capacity != null && capacity > 0, "capacity must be positive");
        check(instructorId != null, "instructorId is required");
    }
}
