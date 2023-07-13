package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private final Connection connection;

    public ClientService(Connection connection) {
        this.connection = connection;
    }

    public long create(String name) throws SQLException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        String query = "INSERT INTO client (NAME) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Creating client failed, no ID obtained.");
            }
        }
    }

    public String getById(long id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }

        String query = "SELECT NAME FROM client WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("NAME");
                } else {
                    throw new SQLException("Client not found with ID: " + id);
                }
            }
        }
    }

    public void setName(long id, String name) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        String query = "UPDATE client SET NAME = ? WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setLong(2, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Client not found with ID: " + id);
            }
        }
    }

    public void deleteById(long id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }

        String query = "DELETE FROM client WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Client not found with ID: " + id);
            }
        }
    }

    public List<Client> listAll() throws SQLException {
        String query = "SELECT ID, NAME FROM client";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            List<Client> clients = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("ID");
                String name = resultSet.getString("NAME");
                Client client = new Client(id, name);
                clients.add(client);
            }

            return clients;
        }
    }
}
