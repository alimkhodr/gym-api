package com.gym.repository;

import com.gym.db.Database;
import com.gym.model.GymClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class GymClassRepository extends JdbcRepository<GymClass> {

    public GymClassRepository(Database database) {
        super(database);
    }

    public List<GymClass> findByInstructorId(long instructorId) {
        return findBy("instructor_id", instructorId);
    }

    @Override
    protected String table() {
        return "gym_classes";
    }

    @Override
    protected List<String> columns() {
        return List.of("name", "schedule", "capacity", "instructor_id");
    }

    @Override
    protected List<Object> values(GymClass gymClass) {
        return Arrays.asList(gymClass.getName(), gymClass.getSchedule(), gymClass.getCapacity(), gymClass.getInstructorId());
    }

    @Override
    protected GymClass map(ResultSet resultSet) throws SQLException {
        GymClass gymClass = new GymClass();
        gymClass.setId(resultSet.getLong("id"));
        gymClass.setName(resultSet.getString("name"));
        gymClass.setSchedule(resultSet.getString("schedule"));
        gymClass.setCapacity(resultSet.getInt("capacity"));
        gymClass.setInstructorId(resultSet.getLong("instructor_id"));
        return gymClass;
    }
}
