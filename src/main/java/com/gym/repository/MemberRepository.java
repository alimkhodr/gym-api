package com.gym.repository;

import com.gym.db.Database;
import com.gym.model.Member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MemberRepository extends JdbcRepository<Member> {

    public MemberRepository(Database database) {
        super(database);
    }

    public List<Member> findByPlanId(long planId) {
        return findBy("plan_id", planId);
    }

    @Override
    protected String table() {
        return "members";
    }

    @Override
    protected List<String> columns() {
        return List.of("name", "email", "phone", "plan_id");
    }

    @Override
    protected List<Object> values(Member member) {
        return Arrays.asList(member.getName(), member.getEmail(), member.getPhone(), member.getPlanId());
    }

    @Override
    protected Member map(ResultSet resultSet) throws SQLException {
        Member member = new Member();
        member.setId(resultSet.getLong("id"));
        member.setName(resultSet.getString("name"));
        member.setEmail(resultSet.getString("email"));
        member.setPhone(resultSet.getString("phone"));
        member.setPlanId(resultSet.getLong("plan_id"));
        return member;
    }
}
