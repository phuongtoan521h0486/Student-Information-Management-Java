package org.thd.DAO;

import org.thd.Models.LoginHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginHistoryDAO implements Repository<LoginHistory, Integer> {
    @Override
    public Integer add(LoginHistory item) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO login_history (account_id, login_time) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, item.getAccountId());
            preparedStatement.setTimestamp(2, new Timestamp(item.getLoginTime().getTime()));
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<LoginHistory> readAll() {
        List<LoginHistory> loginHistories = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM login_history ORDER BY id DESC ")) {

            while (resultSet.next()) {
                LoginHistory loginHistory = resultSetToLoginHistory(resultSet);
                loginHistories.add(loginHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loginHistories;
    }

    @Override
    public LoginHistory read(Integer id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM login_history WHERE id=? ORDER BY id DESC")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSetToLoginHistory(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<LoginHistory> readAllByAccountId(Integer accountId) {
        List<LoginHistory> loginHistories = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM login_history WHERE account_id=? ORDER BY id DESC ")) {

            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                LoginHistory loginHistory = resultSetToLoginHistory(resultSet);
                loginHistories.add(loginHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loginHistories;
    }

    @Override
    public boolean update(LoginHistory item) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE login_history SET account_id=?, login_time=? WHERE id=?")) {

            preparedStatement.setInt(1, item.getAccountId());
            preparedStatement.setTimestamp(2, new Timestamp(item.getLoginTime().getTime()));
            preparedStatement.setInt(3, item.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM login_history WHERE id=?")) {

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private LoginHistory resultSetToLoginHistory(ResultSet resultSet) throws SQLException {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setId(resultSet.getInt("id"));
        loginHistory.setAccountId(resultSet.getInt("account_id"));
        loginHistory.setLoginTime(resultSet.getTimestamp("login_time"));
        return loginHistory;
    }
}
