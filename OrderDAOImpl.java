package main.dao.impl;

import main.connection.ConnectionDB;
import main.dao.OrderDAO;
import main.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrderDAOImpl extends ModelDAOImpl<Order> implements OrderDAO {

    public OrderDAOImpl(Connection connection) {
        super(connection);
    }

    public OrderDAOImpl(ConnectionDB connectionDB) throws SQLException {
        super(connectionDB);
    }

    @Override
    public void add(Order entity) throws SQLException {
        String sql = "INSERT INTO " + getTableName() + " (company_id,project_id,customer_id) VALUES (?,?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, entity.getCompany_id());
            statement.setLong(2, entity.getProject_id());
            statement.setLong(3, entity.getCustomer_id());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Order entity) throws SQLException {
        String sql = "UPDATE " + getTableName() + " SET company_id = ?, project_id = ?, customer_id = ?  WHERE order_id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, entity.getCompany_id());
            statement.setLong(2, entity.getProject_id());
            statement.setLong(3, entity.getCustomer_id());
            statement.setLong(4, entity.getOrder_id());
            statement.executeUpdate();
        }
    }

    @Override
    public Order get(long id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE order_id = ?";
        Order entity = null;
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
    public void remove(Order entity) throws SQLException {
        remove(entity.getOrder_id());
    }

    @Override
    public void remove(long id) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + " WHERE order_id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Collection<Order> getAll() throws SQLException {
        String sql = "SELECT * FROM " + getTableName();
        List<Order> entity = new ArrayList<>();
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                entity.add(prepare(resultSet));
            }
        }
        return entity;
    }

    @Override
    protected Order prepare(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setOrder_id(resultSet.getLong("order_id"));
        order.setCompany_id(resultSet.getLong("company_id"));
        order.setProject_id(resultSet.getLong("project_id"));
        order.setCustomer_id(resultSet.getLong("customer_id"));
        return order;
    }

    @Override
    protected String getTableName() {
        return "order";
    }
}
