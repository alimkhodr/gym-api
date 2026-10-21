package com.gym.service;

import com.gym.model.Member;
import com.gym.model.Plan;
import com.gym.repository.MemberRepository;

import java.util.List;

public class MemberService extends CrudService<Member> {

    private final MemberRepository members;
    private final CrudService<Plan> plans;

    public MemberService(MemberRepository members, CrudService<Plan> plans) {
        super(members, "member");
        this.members = members;
        this.plans = plans;
    }

    public List<Member> findByPlan(long planId) {
        plans.findById(planId);
        return members.findByPlanId(planId);
    }

    @Override
    protected void checkReferences(Member member) {
        plans.checkExists(member.getPlanId());
    }
}
