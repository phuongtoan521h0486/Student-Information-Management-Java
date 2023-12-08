package org.thd.Views.StudentManagement;

import javax.swing.*;
import java.awt.*;

public class FormDetailStudent extends JFrame{
    private JPanel panelMain;
    private JTabbedPane tabbedPane;
    private JTable tableCertificates;

    public FormDetailStudent() {
        setTitle("Detail of <student>");
        setSize(720, 720);
        setLocationRelativeTo(null);
        
        add(panelMain);
    }
}
