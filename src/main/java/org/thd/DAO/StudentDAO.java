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

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Students (studentId, email, name, gender, major, gpa, trainingPoint,picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, student.getStudentId());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setString(3, student.getName());
            preparedStatement.setBoolean(4, student.isGender());
            preparedStatement.setString(5, student.getMajor());
            preparedStatement.setDouble(6, student.getGpa());
            preparedStatement.setInt(7, student.getTrainingPoint());
            preparedStatement.setBytes(8, student.getPicture());

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

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Students")) {

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

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Students WHERE studentId = ?")) {

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

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE Students SET email = ?, name = ?, gender = ?, major = ?, gpa = ?, trainingPoint = ?, picture = ? WHERE studentId = ?")) {

            preparedStatement.setString(1, student.getEmail());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setBoolean(3, student.isGender());
            preparedStatement.setString(4, student.getMajor());
            preparedStatement.setDouble(5, student.getGpa());
            preparedStatement.setInt(6, student.getTrainingPoint());
            preparedStatement.setBytes(7, student.getPicture());
            preparedStatement.setString(8, student.getStudentId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Students WHERE studentId = ?")) {

            preparedStatement.setString(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Student> searchCriteria(List<String> filter, List<String> sort) {
        List<Student> students = new ArrayList<>();
        StringBuilder condition = new StringBuilder();
        StringBuilder sortCriteria = new StringBuilder();

        String sql = "SELECT * FROM Students";
        if (!filter.isEmpty() && !sort.isEmpty()) {
            condition.append(filter.get(0));
            for (int i = 1; i < filter.size(); i++) {
                condition.append(" AND " + filter.get(i));
            }

            sortCriteria.append(sort.get(0));
            for (int i = 1; i < sort.size(); i++) {
                sortCriteria.append(", " + sort.get(i));
            }
            sql = sql + " WHERE " + condition.toString() + " ORDER BY " + sortCriteria.toString();
        } else if (filter.isEmpty() && ! sort.isEmpty()) {
            sortCriteria.append(sort.get(0));
            for (int i = 1; i < sort.size(); i++) {
                sortCriteria.append(", " + sort.get(i));
            }

            sql = sql + " ORDER BY " + sortCriteria.toString();
        } else if (sort.isEmpty() && !filter.isEmpty()) {
            condition.append(filter.get(0));
            for (int i = 1; i < filter.size(); i++) {
                condition.append(" AND " + filter.get(i));
            }

            sql = sql + " WHERE " + condition.toString();
        }
        System.out.println(sql);
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Student student = createStudentFromResultSet(resultSet);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Student> searchStudent(String search) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Students WHERE studentid LIKE ? OR name LIKE ? OR email LIKE ? OR major LIKE ?")) {

            preparedStatement.setString(1, "%"+search+"%");
            preparedStatement.setString(2, "%"+search+"%");
            preparedStatement.setString(3, "%"+search+"%");
            preparedStatement.setString(4, "%"+search+"%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Student student = createStudentFromResultSet(resultSet);
                    students.add(student);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    private Student createStudentFromResultSet(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setStudentId(resultSet.getString("studentId"));
        student.setEmail(resultSet.getString("email"));
        student.setName(resultSet.getString("name"));
        student.setGender(resultSet.getBoolean("gender"));
        student.setMajor(resultSet.getString("major"));
        student.setGpa(resultSet.getDouble("gpa"));
        student.setTrainingPoint(resultSet.getInt("trainingPoint"));
        student.setPicture(resultSet.getBytes("picture"));
        return student;
    }
}

