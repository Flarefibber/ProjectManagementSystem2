package main.dao.impl;

import main.connection.ConnectionDB;
import main.dao.CompaniesDAO;
import main.model.Companies;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompaniesDAOImpl extends ModelDAOImpl<Companies> implements CompaniesDAO {
    public CompaniesDAOImpl(Connection connection) {
        super(connection);
    }

    public CompaniesDAOImpl(ConnectionDB connectionDB) throws SQLException {
        super(connectionDB);
    }

    @Override
    public void add(Companies entity) throws SQLException {
        String sql = "INSERT INTO " + getTableName() + " (name_company) VALUES (?)";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName_company());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Companies entity) throws SQLException {
        String sql = "UPDATE " + getTableName() + " SET name_company = ? WHERE company_id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName_company());
            statement.setLong(2, entity.getCompany_id());
            statement.executeUpdate();
        }
    }

    @Override
    public Companies get(long id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE company_id = ?";
        Companies entity = null;
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
    public void remove(Companies entity) throws SQLException {
        remove(entity.getCompany_id());
    }

    @Override
    public void remove(long id) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + " WHERE company_id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Collection<Companies> getAll() throws SQLException {
        String sql = "SELECT * FROM " + getTableName();
        List<Companies> entity = new ArrayList<>();
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                entity.add(prepare(resultSet));
            }
        }
        return entity;
    }

    @Override
    protected Companies prepare(ResultSet resultSet) throws SQLException {
        Companies companies = new Companies();
        companies.setCompany_id(resultSet.getLong("company_id"));
        companies.setName_company(resultSet.getString("name_company"));
        return companies;
    }

    @Override
    protected String getTableName() {
        return "companies";
    }
}
