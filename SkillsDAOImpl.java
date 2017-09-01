package main.dao.impl;

import main.connection.ConnectionDB;
import main.dao.SkillsDAO;
import main.model.Skills;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SkillsDAOImpl extends ModelDAOImpl<Skills> implements SkillsDAO {
    public SkillsDAOImpl(Connection connection) {
        super(connection);
    }

    public SkillsDAOImpl(ConnectionDB connectionDB) throws SQLException {
        super(connectionDB);
    }

    @Override
    public void add(Skills entity) throws SQLException {
        String sql = "INSERT INTO " + getTableName() + " (skill) VALUES (?)";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, entity.getSkill());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Skills entity) throws SQLException {
        String sql = "UPDATE " + getTableName() + " SET skill = ?  WHERE skill_id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, entity.getSkill());
            statement.setLong(2, entity.getSkill_id());
            statement.executeUpdate();
        }
    }

    @Override
    public Skills get(long id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE skill_id = ?";
        Skills entity = null;
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
    public void remove(Skills entity) throws SQLException {
        remove(entity.getSkill_id());
    }

    @Override
    public void remove(long id) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + " WHERE skill_id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Collection<Skills> getAll() throws SQLException {
        String sql = "SELECT * FROM " + getTableName();
        List<Skills> entity = new ArrayList<>();
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                entity.add(prepare(resultSet));
            }
        }
        return entity;
    }

    @Override
    protected Skills prepare(ResultSet resultSet) throws SQLException {
        Skills skills = new Skills();
        skills.setSkill_id(resultSet.getLong("skill_id"));
        skills.setSkill(resultSet.getString("skill"));
        return skills;
    }

    @Override
    protected String getTableName() {
        return "skills";
    }
}
