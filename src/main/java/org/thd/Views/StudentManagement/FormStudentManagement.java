package org.thd.Views.StudentManagement;

import org.thd.Components.ImageRenderer;
import org.thd.Controllers.StudentController;
import org.thd.Models.StudentTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormStudentManagement extends JFrame{
    private JPanel panelMain;
    private JTable tableStudents;
    private JButton importButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField textFieldID;
    private JTextField textFieldEmail;
    private JTextField textFieldName;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JTextField textFieldMajor;
    private JButton uploadButton;
    private JScrollPane myScroll;
    private JButton exportExcelButton;
    private JButton detailButton;

    private StudentController studentController;

    public FormStudentManagement() {
        setTitle("Student Management Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 720);
        setLocationRelativeTo(null);

        studentController = new StudentController();
        StudentTableModel model = new StudentTableModel(studentController.getAllStudents());
        tableStudents.setModel(model);

        tableStudents.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer(145, 193));

        add(panelMain);
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}


