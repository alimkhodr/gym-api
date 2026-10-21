package com.gym.repository;

import com.gym.db.Database;
import com.gym.model.BaseEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class JdbcRepository<T extends BaseEntity> implements CrudRepository<T> {

    private final Database database;

    protected JdbcRepository(Database database) {
        this.database = database;
    }

    protected abstract String table();

    protected abstract List<String> columns();

    protected abstract List<Object> values(T entity);

    protected abstract T map(ResultSet resultSet) throws SQLException;

    @Override
    public List<T> findAll() {
        return database.query("SELECT * FROM " + table() + " ORDER BY id", this::map);
    }

    @Override
    public Optional<T> findById(long id) {
        return database.query("SELECT * FROM " + table() + " WHERE id = ?", this::map, id)
                .stream()
                .findFirst();
    }

    @Override
    public T save(T entity) {
        if (entity.getId() == null) {
            insert(entity);
        } else {
            update(entity);
        }
        return entity;
    }

    @Override
    public boolean deleteById(long id) {
        return database.update("DELETE FROM " + table() + " WHERE id = ?", id) > 0;
    }

    protected List<T> findBy(String column, Object value) {
        return database.query("SELECT * FROM " + table() + " WHERE " + column + " = ? ORDER BY id", this::map, value);
    }

    private void insert(T entity) {
        String placeholders = String.join(", ", Collections.nCopies(columns().size(), "?"));
        String sql = "INSERT INTO " + table() + " (" + String.join(", ", columns()) + ") VALUES (" + placeholders + ")";
        entity.setId(database.insert(sql, values(entity).toArray()));
    }

    private void update(T entity) {
        String assignments = columns().stream().map(column -> column + " = ?").collect(Collectors.joining(", "));
        List<Object> params = new ArrayList<>(values(entity));
        params.add(entity.getId());
        database.update("UPDATE " + table() + " SET " + assignments + " WHERE id = ?", params.toArray());
    }
}
