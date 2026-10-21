package com.gym.repository;

import com.gym.db.Database;

import java.util.List;

public class LinkRepository {

    private final Database database;
    private final String table;
    private final String ownerColumn;
    private final String targetColumn;

    public LinkRepository(Database database, String table, String ownerColumn, String targetColumn) {
        this.database = database;
        this.table = table;
        this.ownerColumn = ownerColumn;
        this.targetColumn = targetColumn;
    }

    public void link(long ownerId, long targetId) {
        database.update("INSERT INTO " + table + " (" + ownerColumn + ", " + targetColumn + ") VALUES (?, ?)", ownerId, targetId);
    }

    public boolean unlink(long ownerId, long targetId) {
        return database.update("DELETE FROM " + table + " WHERE " + ownerColumn + " = ? AND " + targetColumn + " = ?", ownerId, targetId) > 0;
    }

    public List<Long> findTargets(long ownerId) {
        return ids(targetColumn, ownerColumn, ownerId);
    }

    public List<Long> findOwners(long targetId) {
        return ids(ownerColumn, targetColumn, targetId);
    }

    private List<Long> ids(String select, String where, long id) {
        String sql = "SELECT " + select + " FROM " + table + " WHERE " + where + " = ? ORDER BY " + select;
        return database.query(sql, resultSet -> resultSet.getLong(1), id);
    }
}
