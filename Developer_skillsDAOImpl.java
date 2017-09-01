package main.dao.impl;

import main.connection.ConnectionDB;
import main.dao.Developer_skillsDAO;
import main.model.Developer_skills;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Developer_skillsDAOImpl extends ModelDAOImpl<Developer_skills> implements Developer_skillsDAO {
    public Developer_skillsDAOImpl(Connection connection) {
        super(connection);
    }

    public Developer_skillsDAOImpl(ConnectionDB connectionDB) throws SQLException {
        super(connectionDB);
    }

    @Override
    public void add(Developer_skills entity) throws SQLException {
        String sql = "INSERT INTO " + getTableName() + " (developer_id,skill_id) VALUES (?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, entity.getDeveloper_id());
            statement.setLong(2, entity.getSkill_id());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Developer_skills entity) throws SQLException {
        String sql = "UPDATE " + getTableName() + " SET developer_id = ?, skill_id = ?   WHERE developer_skills_id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, entity.getDeveloper_id());
            statement.setLong(2, entity.getSkill_id());
            statement.setLong(3, entity.getDeveloper_skills_id());
            statement.executeUpdate();
        }
    }

    @Override
    public Developer_skills get(long id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE developer_skills_id = ?";
        Developer_skills entity = null;
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
    public void remove(Developer_skills entity) throws SQLException {
        remove(entity.getDeveloper_skills_id());
    }

    @Override
    public void remove(long id) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + " WHERE developer_skills_id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Collection<Developer_skills> getAll() throws SQLException {
        String sql = "SELECT * FROM " + getTableName();
        List<Developer_skills> entity = new ArrayList<>();
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                entity.add(prepare(resultSet));
            }
        }
        return entity;
    }

    @Override
    protected Developer_skills prepare(ResultSet resultSet) throws SQLException {
        Developer_skills developer_skills = new Developer_skills();
        developer_skills.setDeveloper_skills_id(resultSet.getLong("developer_skills_id"));
        developer_skills.setDeveloper_id(resultSet.getLong("developer_id"));
        developer_skills.setSkill_id(resultSet.getLong("skill_id"));
        return developer_skills;
    }

    @Override
    protected String getTableName() {
        return "developer_skills";
    }
}
