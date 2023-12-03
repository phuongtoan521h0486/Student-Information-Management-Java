package org.thd.Views;

import javax.swing.*;

public class FormMain extends JFrame{
    private JPanel panelMain;
    private JLabel image;
    private JLabel displayName;
    private JButton buttonHome;
    private JButton buttonUser;

    public FormMain() {
        setTitle("Main Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 720);
        setLocationRelativeTo(null);

        add(panelMain);
    }

}
