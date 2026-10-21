package com.gym.repository;

import com.gym.db.Database;
import com.gym.model.Equipment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class EquipmentRepository extends JdbcRepository<Equipment> {

    public EquipmentRepository(Database database) {
        super(database);
    }

    @Override
    protected String table() {
        return "equipment";
    }

    @Override
    protected List<String> columns() {
        return List.of("name", "quantity");
    }

    @Override
    protected List<Object> values(Equipment equipment) {
        return Arrays.asList(equipment.getName(), equipment.getQuantity());
    }

    @Override
    protected Equipment map(ResultSet resultSet) throws SQLException {
        Equipment equipment = new Equipment();
        equipment.setId(resultSet.getLong("id"));
        equipment.setName(resultSet.getString("name"));
        equipment.setQuantity(resultSet.getInt("quantity"));
        return equipment;
    }
}
