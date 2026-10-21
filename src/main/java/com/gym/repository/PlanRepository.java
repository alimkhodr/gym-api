package com.gym.repository;

import com.gym.db.Database;
import com.gym.model.Plan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class PlanRepository extends JdbcRepository<Plan> {

    public PlanRepository(Database database) {
        super(database);
    }

    @Override
    protected String table() {
        return "plans";
    }

    @Override
    protected List<String> columns() {
        return List.of("name", "monthly_price", "duration_months");
    }

    @Override
    protected List<Object> values(Plan plan) {
        return Arrays.asList(plan.getName(), plan.getMonthlyPrice(), plan.getDurationMonths());
    }

    @Override
    protected Plan map(ResultSet resultSet) throws SQLException {
        Plan plan = new Plan();
        plan.setId(resultSet.getLong("id"));
        plan.setName(resultSet.getString("name"));
        plan.setMonthlyPrice(resultSet.getBigDecimal("monthly_price"));
        plan.setDurationMonths(resultSet.getInt("duration_months"));
        return plan;
    }
}
