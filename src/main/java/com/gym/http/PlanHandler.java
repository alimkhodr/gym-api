package com.gym.http;

import com.gym.model.Plan;
import com.gym.service.CrudService;
import com.gym.service.MemberService;

public class PlanHandler extends CrudHandler<Plan> {

    private final MemberService members;

    public PlanHandler(CrudService<Plan> plans, MemberService members) {
        super(plans, Plan.class);
        this.members = members;
    }

    @Override
    protected Response nested(Request request, long id) {
        if (request.size() == 2 && request.segment(1).equals("members")) {
            return get(request, () -> members.findByPlan(id));
        }
        return super.nested(request, id);
    }
}
