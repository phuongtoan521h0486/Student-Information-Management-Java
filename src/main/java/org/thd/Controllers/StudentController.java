package org.thd.Controllers;

import org.thd.DAO.AccountDAO;
import org.thd.DAO.StudentDAO;
import org.thd.Models.Student;

import java.util.List;

public class StudentController {
    private StudentDAO studentDAO;

    public StudentController() {
        this.studentDAO = new StudentDAO();
    }

    public String addStudent(Student student) {
        return studentDAO.add(student);
    }

    public List<Student> getAllStudents() {
        return studentDAO.readAll();
    }

    public Student getStudentById(String studentId) {
        return studentDAO.read(studentId);
    }

    public boolean updateStudent(Student student) {
        return studentDAO.update(student);
    }

    public boolean deleteStudent(String studentId) {
        return studentDAO.delete(studentId);
    }

    private List<Student> searchCriteria(List<String> filter, List<String> sort) {
        return studentDAO.searchCriteria(filter, sort);
    }

    public List<Student> searchStudent(String search) {
        return studentDAO.searchStudent(search);
    }
}
