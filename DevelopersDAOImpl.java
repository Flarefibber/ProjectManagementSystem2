package main.dao.impl;

import main.connection.ConnectionDB;
import main.dao.DevelopersDAO;
import main.model.Developers;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DevelopersDAOImpl extends ModelDAOImpl<Developers> implements DevelopersDAO {
    public DevelopersDAOImpl(Connection connection) {
        super(connection);
    }

    public DevelopersDAOImpl(ConnectionDB connectionDB) throws SQLException {
        super(connectionDB);
    }

    @Override
    public void add(Developers entity) throws SQLException {
        String sql = "INSERT INTO " + getTableName() + " (name,surname,patronymic) VALUES (?,?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSurname());
            statement.setString(3, entity.getPatronymic());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Developers entity) throws SQLException {
        String sql = "UPDATE " + getTableName() + " SET name = ?, surname = ? , patronymic = ?  WHERE developer_id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSurname());
            statement.setString(3, entity.getPatronymic());
            statement.setLong(4, entity.getDeveloper_id());
            statement.executeUpdate();
        }
    }

    @Override
    public Developers get(long id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE developer_id = ?";
        Developers entity = null;
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
    public void remove(Developers entity) throws SQLException {
        remove(entity.getDeveloper_id());
    }

    @Override
    public void remove(long id) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + " WHERE developer_id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Collection<Developers> getAll() throws SQLException {
        String sql = "SELECT * FROM " + getTableName();
        List<Developers> entity = new ArrayList<>();
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                entity.add(prepare(resultSet));
            }
        }
        return entity;
    }

    @Override
    protected Developers prepare(ResultSet resultSet) throws SQLException {
        Developers developers = new Developers();
        developers.setDeveloper_id(resultSet.getLong("developer_id"));
        developers.setName(resultSet.getString("name"));
        developers.setSurname(resultSet.getString("surname"));
        developers.setPatronymic(resultSet.getString("patronymic"));
        return developers;
    }

    @Override
    protected String getTableName() {
        return "developers";
    }
}
