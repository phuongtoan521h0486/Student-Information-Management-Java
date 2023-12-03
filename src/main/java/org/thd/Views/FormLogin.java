package org.thd.Views;

import org.thd.Controllers.AccountController;
import org.thd.Models.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormLogin extends JFrame {

    private JTextField textFieldUsername;
    private JPasswordField passwordFieldPassword;
    private JButton buttonLogin;

    private AccountController accountController;

    public FormLogin() {
        setTitle("Login Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);

        // Initialize the AccountController
        accountController = new AccountController();

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Username:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        textFieldUsername = new JTextField(20);
        panel.add(textFieldUsername, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(new JLabel("Password:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        passwordFieldPassword = new JPasswordField(20);
        panel.add(passwordFieldPassword, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        buttonLogin = new JButton("Login");
        panel.add(buttonLogin, constraints);


        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textFieldUsername.getText();
                String password = new String(passwordFieldPassword.getPassword());

                // Authenticate user
                Account authenticatedAccount = accountController.authenticateUser(username, password);

                if (authenticatedAccount != null) {
                    String userRole = authenticatedAccount.getRole();
                    JOptionPane.showMessageDialog(FormLogin.this, "Login successful! User role: " + userRole);


                    if ("admin".equals(userRole)) {

                    } else if ("manager".equals(userRole)) {

                    } else if ("employee".equals(userRole)) {

                    }

                    dispose();
                } else {
                    JOptionPane.showMessageDialog(FormLogin.this, "Login failed. Invalid username or password.");
                }
            }
        });
        add(panel);
    }

}
