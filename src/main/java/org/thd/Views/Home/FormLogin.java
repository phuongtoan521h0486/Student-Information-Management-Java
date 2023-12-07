package org.thd.Views.Home;

import org.thd.Controllers.AccountController;
import org.thd.Controllers.LoginHistoryController;
import org.thd.Models.Account;
import org.thd.Models.LoginHistory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormLogin extends JFrame{
    private JPanel panelMain;
    private JPanel panelHeader;
    private JLabel labelLogin;
    private JTextField textFieldUsername;
    private JPasswordField passwordFieldPassword;
    private JButton buttonLogin;
    private AccountController accountController;
    private LoginHistoryController loginHistoryController;
    public FormLogin() {
        setTitle("Login Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);

        accountController = new AccountController();
        loginHistoryController = new LoginHistoryController();

        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textFieldUsername.getText();
                String password = new String(passwordFieldPassword.getPassword());

                // Authenticate user
                Account authenticatedAccount = accountController.authenticateUser(username, password);

                if (!authenticatedAccount.isStatus()) {
                    JOptionPane.showMessageDialog(FormLogin.this, "Login failed. Your account is disabled by admin.");
                } else {
                    if (authenticatedAccount != null) {
                        String userRole = authenticatedAccount.getRole();
                        JOptionPane.showMessageDialog(FormLogin.this, "Login successful! User role: " + userRole);
                        new FormMain(authenticatedAccount).setVisible(true);

                        loginHistoryController.addLoginHistory(new LoginHistory(authenticatedAccount.getId()));

                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(FormLogin.this, "Login failed. Invalid username or password.");
                    }
                }
            }
        });
        add(panelMain);

    }
}
