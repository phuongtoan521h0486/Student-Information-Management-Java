package org.thd.DAO;

import org.mindrot.jbcrypt.BCrypt;
import org.thd.Models.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements Repository<Account, Integer> {

    @Override
    public Integer add(Account account) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO accounts (username, password, role, picture, name, age, phoneNumber, status) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            String hashedPassword = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, account.getRole());
            preparedStatement.setBytes(4, account.getPicture());
            preparedStatement.setString(5, account.getName());
            preparedStatement.setInt(6, account.getAge());
            preparedStatement.setString(7, account.getPhoneNumber());
            preparedStatement.setBoolean(8, account.isStatus());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Creating account failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating account failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Account> readAll() {
        List<Account> accounts = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts")) {

            while (resultSet.next()) {
                Account account = extractAccountFromResultSet(resultSet);
                accounts.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }
    public ResultSet readAllResultSet() {
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts")) {

            return resultSet;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account read(Integer id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE id=?")) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractAccountFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResultSet readResultset(Integer id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE id=?")) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean update(Account account) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE accounts SET username=?, password=?, role=?, picture=?, name=?, age=?, phoneNumber=?, status=? WHERE id=?")) {

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.setString(3, account.getRole());
            preparedStatement.setBytes(4, account.getPicture());
            preparedStatement.setString(5, account.getName());
            preparedStatement.setInt(6, account.getAge());
            preparedStatement.setString(7, account.getPhoneNumber());
            preparedStatement.setBoolean(8, account.isStatus());
            preparedStatement.setInt(9, account.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM accounts WHERE id=?")) {

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePicture(Integer id, byte[] newPicture) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE accounts SET picture=? WHERE id=?")) {

            preparedStatement.setBytes(1, newPicture);
            preparedStatement.setInt(2, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Account extractAccountFromResultSet(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt("id"));
        account.setUsername(resultSet.getString("username"));
        account.setPassword(resultSet.getString("password"));
        account.setRole(resultSet.getString("role"));
        account.setPicture(resultSet.getBytes("picture"));
        account.setName(resultSet.getString("name"));
        account.setAge(resultSet.getInt("age"));
        account.setPhoneNumber(resultSet.getString("phoneNumber"));
        account.setStatus(resultSet.getBoolean("status"));
        return account;
    }

    public Account getAccountByUsername(String username) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE username=?")) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractAccountFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
