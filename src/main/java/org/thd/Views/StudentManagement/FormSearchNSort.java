package org.thd.Views.StudentManagement;

import org.thd.Controllers.StudentController;
import org.thd.Models.Student;
import org.thd.Models.StudentTableModel;

import javax.swing.*;
import java.util.ArrayList;

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
    private JComboBox comboBox2;
    private JComboBox comboBox1;
    private JButton applySortButton;
    private StudentController studentController;
    public FormSearchNSort() {
        setTitle("Search Student Form");
        setSize(720, 720);
        setLocationRelativeTo(null);

        studentController = new StudentController();
        tableStudents.setModel(new StudentTableModel(new ArrayList<Student>(){})); // init headers

        add(panelMain);
    }
}
