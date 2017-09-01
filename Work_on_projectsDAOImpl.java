package main.dao.impl;

import main.connection.ConnectionDB;
import main.dao.Work_on_projectsDAO;
import main.model.Work_on_projects;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Work_on_projectsDAOImpl extends ModelDAOImpl<Work_on_projects> implements Work_on_projectsDAO {
    public Work_on_projectsDAOImpl(Connection connection) {
        super(connection);
    }

    public Work_on_projectsDAOImpl(ConnectionDB connectionDB) throws SQLException {
        super(connectionDB);
    }

    @Override
    public void add(Work_on_projects entity) throws SQLException {
        String sql = "INSERT INTO " + getTableName() + " (developer_id,project_id) VALUES (?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, entity.getDeveloper_id());
            statement.setLong(2, entity.getProject_id());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Work_on_projects entity) throws SQLException {
        String sql = "UPDATE " + getTableName() + " SET developer_id = ?, project_id = ?  WHERE work_on_projects_id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, entity.getDeveloper_id());
            statement.setLong(2, entity.getProject_id());
            statement.setLong(3, entity.getWork_on_projects_id());
            statement.executeUpdate();
        }
    }

    @Override
    public Work_on_projects get(long id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE work_on_projects_id = ?";
        Work_on_projects entity = null;
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
    public void remove(Work_on_projects entity) throws SQLException {
        remove(entity.getWork_on_projects_id());
    }

    @Override
    public void remove(long id) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + " WHERE work_on_projects_id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Collection<Work_on_projects> getAll() throws SQLException {
        String sql = "SELECT * FROM " + getTableName();
        List<Work_on_projects> entity = new ArrayList<>();
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                entity.add(prepare(resultSet));
            }
        }
        return entity;
    }

    @Override
    protected Work_on_projects prepare(ResultSet resultSet) throws SQLException {
        Work_on_projects work_on_projects = new Work_on_projects();
        work_on_projects.setWork_on_projects_id(resultSet.getLong("work_on_projects_id"));
        work_on_projects.setDeveloper_id(resultSet.getLong("developer_id"));
        work_on_projects.setProject_id(resultSet.getLong("project_id"));
        return work_on_projects;
    }

    @Override
    protected String getTableName() {
        return "work_on_projects";
    }
}
