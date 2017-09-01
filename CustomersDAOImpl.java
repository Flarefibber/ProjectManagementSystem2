package main.dao.impl;

import main.connection.ConnectionDB;
import main.dao.CustomersDAO;
import main.model.Customers;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomersDAOImpl extends ModelDAOImpl<Customers> implements CustomersDAO {
    public CustomersDAOImpl(Connection connection) {
        super(connection);
    }

    public CustomersDAOImpl(ConnectionDB connectionDB) throws SQLException {
        super(connectionDB);
    }

    @Override
    public void add(Customers entity) throws SQLException {
        String sql = "INSERT INTO " + getTableName() + " (name_customer) VALUES (?)";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName_customer());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Customers entity) throws SQLException {
        String sql = "UPDATE " + getTableName() + " SET name_customer = ? WHERE customer_id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName_customer());
            statement.setLong(2, entity.getCustomer_id());
            statement.executeUpdate();
        }
    }

    @Override
    public Customers get(long id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE customer_id = ?";
        Customers entity = null;
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                entity = prepare(resultSet);
            }
        }
        return entity;
    }

    @Override
    public void remove(Customers entity) throws SQLException {
        remove(entity.getCustomer_id());
    }

    @Override
    public void remove(long id) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + " WHERE customer_id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Collection<Customers> getAll() throws SQLException {
        String sql = "SELECT * FROM " + getTableName();
        List<Customers> entity = new ArrayList<>();
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                entity.add(prepare(resultSet));
            }
        }
        return entity;
    }

    @Override
    protected Customers prepare(ResultSet resultSet) throws SQLException {
        Customers customers = new Customers();
        customers.setCustomer_id(resultSet.getLong("customer_id"));
        customers.setName_customer(resultSet.getString("name_customer"));
        return customers;
    }

    @Override
    protected String getTableName() {
        return "customers";
    }
}
