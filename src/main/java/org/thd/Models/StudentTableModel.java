package org.thd.Models;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StudentTableModel extends AbstractTableModel {
    private List<Student> students;
    private String[] columnNames = {"Image", "Student ID", "Email", "Name", "Gender", "Major"};

    public StudentTableModel(List<Student> students) {
        this.students = students;
    }
    @Override
    public int getRowCount() {
        return students.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student student = students.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return byteArrayToImageIcon(student.getPicture());
            case 1:
                return student.getStudentId();
            case 2:
                return student.getEmail();
            case 3:
                return student.getName();
            case 4:
                return student.isGender() ? "Male": "Female";
            case 5:
                return student.getMajor();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    private ImageIcon byteArrayToImageIcon(byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            return new ImageIcon(bytes);
        } else {
            return null;
        }
    }
}


