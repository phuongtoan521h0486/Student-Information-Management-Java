package org.thd.Forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormLogin extends JFrame{
    private JPanel panelMain;
    private JTextField textFieldUsername;
    private JTextField textFieldPassword;
    private JButton loginButton;

    public FormLogin() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textFieldUsername.getText();
                String password = textFieldPassword.getText();

                if (username.equals("admin") && password.equals("123456")) {
                    System.out.println("Login success");
                }
                else {
                    System.out.println("Login fail");
                }
            }
        });

        setContentPane(panelMain);
        setTitle("Welcome");
        setMinimumSize(new Dimension(450, 300));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }


}
