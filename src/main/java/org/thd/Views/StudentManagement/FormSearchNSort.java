package org.thd.Views.StudentManagement;

import org.thd.Components.ImageRenderer;
import org.thd.Controllers.StudentController;
import org.thd.Models.Student;
import org.thd.Models.StudentTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FormSearchNSort extends JFrame{
    private JPanel panelMain;
    private JTextField textFieldSearch;
    private JCheckBox maleCheckBox;
    private JCheckBox femaleCheckBox;
    private JComboBox comboBoxGPA;
    private JComboBox comboBoxTrainingPoint;
    private JComboBox comboBoxSortByName;
    private JTable tableStudents;
    private JComboBox comboBoxSortByID;
    private JComboBox comboBoxSortByGPA;
    private JComboBox comboBoxSortByPoint;
    private JButton applySortButton;
    private JButton buttonSearch;
    private StudentController studentController;

    private List<String> filter;
    private List<String> sort;
    public FormSearchNSort() {
        setTitle("Search Student Form");
        setSize(720, 720);
        setLocationRelativeTo(null);

        studentController = new StudentController();
        tableStudents.setModel(new StudentTableModel(new ArrayList<Student>(){})); // init headers
        add(panelMain);
        textFieldSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentTableModel model = new StudentTableModel(studentController.searchStudent(textFieldSearch.getText()));
                tableStudents.setModel(model);
                tableStudents.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer(95, 140));
            }
        });
        applySortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Student> students = studentController.searchStudent(textFieldSearch.getText());

                StudentTableModel model = new StudentTableModel(getFilterCriteria(students));
                tableStudents.setModel(model);
                tableStudents.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer(95, 140));
            }
        });
        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentTableModel model = new StudentTableModel(studentController.searchStudent(textFieldSearch.getText()));
                tableStudents.setModel(model);
                tableStudents.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer(95, 140));
            }
        });
    }

    private List<Student> getFilterCriteria(List<Student> students) {
        List<Student> criteria = new ArrayList<>();
        criteria = students;
        if (maleCheckBox.isSelected()) {
            criteria = students.stream().filter(s -> s.isGender()).collect(Collectors.toList());
        }

        if (femaleCheckBox.isSelected()) {
            criteria = students.stream().filter(s -> !s.isGender()).collect(Collectors.toList());
        }

        switch (comboBoxGPA.getSelectedIndex()) {
            case 1:
                criteria = criteria.stream().filter(s -> s.isGpaA()).collect(Collectors.toList());
                break;
            case 2:
                criteria = criteria.stream().filter(s -> s.isGpaB()).collect(Collectors.toList());
                break;
            case 3:
                criteria = criteria.stream().filter(s -> s.isGpaC()).collect(Collectors.toList());
                break;
            case 4:
                criteria = criteria.stream().filter(s -> s.isGpaD()).collect(Collectors.toList());
                break;
            case 5:
                criteria = criteria.stream().filter(s -> s.isGpaF()).collect(Collectors.toList());
                break;
        }

        switch (comboBoxTrainingPoint.getSelectedIndex()) {
            case 1:
                criteria = criteria.stream().filter(s -> s.isExcellent()).collect(Collectors.toList());
                break;
            case 2:
                criteria = criteria.stream().filter(s -> s.isVeryGood()).collect(Collectors.toList());
                break;
            case 3:
                criteria = criteria.stream().filter(s -> s.isGood()).collect(Collectors.toList());
                break;
            case 4:
                criteria = criteria.stream().filter(s -> s.isAverage()).collect(Collectors.toList());
                break;
            case 5:
                criteria = criteria.stream().filter(s -> s.isBelowAverage()).collect(Collectors.toList());
                break;
            case 6:
                criteria = criteria.stream().filter(s -> s.isWeek()).collect(Collectors.toList());
                break;
        }
        return criteria;
    }

}
