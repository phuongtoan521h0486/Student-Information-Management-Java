package org.thd.DAO;

import org.thd.Models.Certificate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CertificateDAO implements Repository<Certificate, Integer> {
    @Override
    public Integer add(Certificate item) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO certificates (certificateTitle, score, issuedDate, expirationDate, studentId) " +
                             "VALUES (?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, item.getTitle());
            preparedStatement.setDouble(2, item.getScore());
            preparedStatement.setDate(3, new java.sql.Date(item.getIssuedDate().getTime()));
            preparedStatement.setDate(4, item.getExpirationDate() != null ?
                    new java.sql.Date(item.getExpirationDate().getTime()) : null);
            preparedStatement.setString(5, item.getStudentId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating certificate failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating certificate failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Certificate> readAll() {
        List<Certificate> certificates = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM certificates")) {

            while (resultSet.next()) {
                certificates.add(mapResultSetToCertificate(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return certificates;
    }

    @Override
    public Certificate read(Integer id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM certificates WHERE certificateId = ?")) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToCertificate(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Certificate> readAllByStudentId(String id) {
        List<Certificate> certificates = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM certificates WHERE studentId = ?")) {

            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                certificates.add(mapResultSetToCertificate(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return certificates;
    }

    @Override
    public boolean update(Certificate item) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE certificates SET certificateTitle = ?, score = ?, " +
                             "issuedDate = ?, expirationDate = ?, studentId = ? " +
                             "WHERE certificateId = ?")) {

            preparedStatement.setString(1, item.getTitle());
            preparedStatement.setDouble(2, item.getScore());
            preparedStatement.setDate(3, new java.sql.Date(item.getIssuedDate().getTime()));
            preparedStatement.setDate(4, item.getExpirationDate() != null ?
                    new java.sql.Date(item.getExpirationDate().getTime()) : null);
            preparedStatement.setString(5, item.getStudentId());
            preparedStatement.setInt(6, item.getCertificateId());

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean delete(Integer id) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM certificates WHERE certificateId = ?")) {

            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private Certificate mapResultSetToCertificate(ResultSet resultSet) throws SQLException {
        Certificate certificate = new Certificate();
        certificate.setCertificateId(resultSet.getInt("certificateId"));
        certificate.setTitle(resultSet.getString("certificateTitle"));
        certificate.setScore(resultSet.getDouble("score"));
        certificate.setIssuedDate(resultSet.getDate("issuedDate"));
        certificate.setExpirationDate(resultSet.getDate("expirationDate"));
        certificate.setStudentId(resultSet.getString("studentId"));
        return certificate;
    }
}
