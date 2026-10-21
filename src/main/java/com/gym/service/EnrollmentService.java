package com.gym.service;

import com.gym.exception.ConflictException;
import com.gym.model.GymClass;
import com.gym.model.Member;
import com.gym.repository.LinkRepository;

public class EnrollmentService extends AssociationService<GymClass, Member> {

    public EnrollmentService(LinkRepository enrollments, CrudService<GymClass> classes, CrudService<Member> members) {
        super(enrollments, classes, members);
    }

    @Override
    protected void beforeLink(GymClass gymClass, int enrolled) {
        if (gymClass.isFull(enrolled)) {
            throw new ConflictException("class " + gymClass.getId() + " is full");
        }
    }
}
