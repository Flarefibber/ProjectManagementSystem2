package main.dao.impl;

import main.connection.ConnectionDB;
import main.dao.GenericDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ModelDAOImpl<T> implements GenericDAO<T> {
    protected final Connection connection;

    public ModelDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public ModelDAOImpl(ConnectionDB connectionDB) throws SQLException {
        this(connectionDB.getConnection());
    }
    protected abstract T prepare(ResultSet resultSet) throws SQLException;

    protected abstract String getTableName();
}
