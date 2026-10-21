package com.gym.repository;

import com.gym.db.Database;
import com.gym.model.Instructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class InstructorRepository extends JdbcRepository<Instructor> {

    public InstructorRepository(Database database) {
        super(database);
    }

    @Override
    protected String table() {
        return "instructors";
    }

    @Override
    protected List<String> columns() {
        return List.of("name", "email", "phone", "specialty");
    }

    @Override
    protected List<Object> values(Instructor instructor) {
        return Arrays.asList(instructor.getName(), instructor.getEmail(), instructor.getPhone(), instructor.getSpecialty());
    }

    @Override
    protected Instructor map(ResultSet resultSet) throws SQLException {
        Instructor instructor = new Instructor();
        instructor.setId(resultSet.getLong("id"));
        instructor.setName(resultSet.getString("name"));
        instructor.setEmail(resultSet.getString("email"));
        instructor.setPhone(resultSet.getString("phone"));
        instructor.setSpecialty(resultSet.getString("specialty"));
        return instructor;
    }
}
