package com.gym.http;

import com.gym.model.Member;
import com.gym.service.EnrollmentService;
import com.gym.service.MemberService;

public class MemberHandler extends CrudHandler<Member> {

    private final EnrollmentService enrollments;

    public MemberHandler(MemberService members, EnrollmentService enrollments) {
        super(members, Member.class);
        this.enrollments = enrollments;
    }

    @Override
    protected Response nested(Request request, long id) {
        if (request.size() == 2 && request.segment(1).equals("classes")) {
            return get(request, () -> enrollments.findOwners(id));
        }
        return super.nested(request, id);
    }
}
