package main.dao.impl;

import main.connection.ConnectionDB;
import main.dao.ProjectsDAO;
import main.model.Projects;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProjectsDAOImpl extends ModelDAOImpl<Projects> implements ProjectsDAO {
    public ProjectsDAOImpl(Connection connection) {
        super(connection);
    }

    public ProjectsDAOImpl(ConnectionDB connectionDB) throws SQLException {
        super(connectionDB);
    }

    @Override
    public void add(Projects entity) throws SQLException {
        String sql = "INSERT INTO " + getTableName() + " (name_project,projec_start_date,deadline) VALUES (?,?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName_project());
            statement.setDate(2, entity.getProjec_start_date());
            statement.setDate(3, entity.getDeadline());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Projects entity) throws SQLException {
        String sql = "UPDATE " + getTableName() + " SET name_project = ?, projec_start_date = ? , deadline = ?  WHERE project_id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName_project());
            statement.setDate(2, entity.getProjec_start_date());
            statement.setDate(3, entity.getDeadline());
            statement.setLong(4, entity.getProject_id());
            statement.executeUpdate();
        }
    }

    @Override
    public Projects get(long id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE project_id = ?";
        Projects entity = null;
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
    public void remove(Projects entity) throws SQLException {
        remove(entity.getProject_id());
    }

    @Override
    public void remove(long id) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + " WHERE project_id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Collection<Projects> getAll() throws SQLException {
        String sql = "SELECT * FROM " + getTableName();
        List<Projects> entity = new ArrayList<>();
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                entity.add(prepare(resultSet));
            }
        }
        return entity;
    }

    @Override
    protected Projects prepare(ResultSet resultSet) throws SQLException {
        Projects projects = new Projects();
        projects.setProject_id(resultSet.getLong("project_id"));
        projects.setName_project(resultSet.getString("name_project"));
        projects.setProjec_start_date(resultSet.getDate("projec_start_date"));
        projects.setDeadline(resultSet.getDate("deadline"));
        return projects;
    }

    @Override
    protected String getTableName() {
        return "projects";
    }
}
