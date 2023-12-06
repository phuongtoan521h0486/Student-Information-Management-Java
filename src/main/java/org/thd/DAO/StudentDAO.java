package org.thd.DAO;

import org.thd.Models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements Repository<Student, String> {

    @Override
    public String add(Student student) {
        if (read(student.getStudentId()) != null) {
            return null;
        }

        String insertQuery = "INSERT INTO Student (studentId, email, name, gender, major, picture) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, student.getStudentId());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setString(3, student.getName());
            preparedStatement.setBoolean(4, student.isGender());
            preparedStatement.setString(5, student.getMajor());
            preparedStatement.setBytes(6, student.getPicture());

            preparedStatement.executeUpdate();
            return student.getStudentId();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Student> readAll() {
        List<Student> students = new ArrayList<>();
        String selectQuery = "SELECT * FROM Student";
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            while (resultSet.next()) {
                Student student = createStudentFromResultSet(resultSet);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public Student read(String id) {
        String selectQuery = "SELECT * FROM Student WHERE studentId = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createStudentFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Student student) {
        String updateQuery = "UPDATE Student SET email = ?, name = ?, gender = ?, major = ?, picture = ? WHERE studentId = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, student.getEmail());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setBoolean(3, student.isGender());
            preparedStatement.setString(4, student.getMajor());
            preparedStatement.setBytes(5, student.getPicture());
            preparedStatement.setString(6, student.getStudentId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        String deleteQuery = "DELETE FROM Student WHERE studentId = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setString(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Student createStudentFromResultSet(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setStudentId(resultSet.getString("studentId"));
        student.setEmail(resultSet.getString("email"));
        student.setName(resultSet.getString("name"));
        student.setGender(resultSet.getBoolean("gender"));
        student.setMajor(resultSet.getString("major"));
        student.setPicture(resultSet.getBytes("picture"));
        return student;
    }
}

