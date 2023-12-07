package org.thd.Views.AccountManagement;

import org.thd.Controllers.AccountController;
import org.thd.Models.Account;
import org.thd.Models.AccountTableModel;
import org.thd.Views.StudentManagement.FormStudentManagement;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class FormAccountCRUD extends JFrame{
    private JPanel panelMain;
    private JButton buttonUpload;
    private JTextField textFieldName;
    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JComboBox<String> comboBoxRole;
    private JTextField textFieldAge;
    private JTextField textFieldPhoneNumber;
    private JButton addButton;
    private JTable tableAccount;
    private JLabel pictureUpload;
    private JPasswordField passwordFieldConfirm;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton lockButton;
    private JButton backButton;

    private AccountController accountController;
    private byte[] pictureData;

    public FormAccountCRUD() {
        setTitle("Account Management Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 720);
        setLocationRelativeTo(null);

        accountController = new AccountController();
        AccountTableModel model = new AccountTableModel(accountController.getAllAccounts());
        tableAccount.setModel(model);

        tableAccount.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (tableAccount.getSelectedRow() != -1) {
                    String status = String.valueOf(tableAccount.getValueAt(tableAccount.getSelectedRow(), 3));
                    if (status.equals("Locked")) {
                        lockButton.setText("Enable");
                    } else {
                        lockButton.setText("Lock");
                    }

                    textFieldUsername.setText("");
                    comboBoxRole.setSelectedIndex(0);
                    textFieldName.setText("");
                    textFieldAge.setText("");
                    textFieldPhoneNumber.setText("");
                }
            }
        });


        buttonUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileChooser();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pictureData == null) {
                    BufferedImage img = null;
                    try {
                        ClassLoader classLoader = getClass().getClassLoader();
                        URL resource = classLoader.getResource("static/images/default.png");
                        img = ImageIO.read(resource);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        ImageIO.write(img, "jpg", bos );
                        pictureData = bos.toByteArray();
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                }
                createNewAccount();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormUserSystem().setVisible(true);
                dispose();
            }
        });

        add(panelMain);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableAccount.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(FormAccountCRUD.this, "Please chose an account you want to delete", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    int confirm = JOptionPane.showConfirmDialog(FormAccountCRUD.this, "You want to delete this user");
                    if (confirm == JOptionPane.YES_OPTION) {
                        int userID = Integer.parseInt(String.valueOf(tableAccount.getValueAt(row, 0)));
                        accountController.deleteAccount(userID);
                        AccountTableModel model = new AccountTableModel(accountController.getAllAccounts());
                        tableAccount.setModel(model);
                    }
                }
            }
        });
        lockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableAccount.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(FormAccountCRUD.this, "Please chose an account you want to lock", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    int confirm = JOptionPane.showConfirmDialog(FormAccountCRUD.this, "You want to lock this user");
                    if (confirm == JOptionPane.YES_OPTION) {
                        int userID = Integer.parseInt(String.valueOf(tableAccount.getValueAt(row, 0)));
                        Account user = accountController.getAccountById(userID);
                        if (lockButton.getText().equals("Lock")) {
                            user.setStatus(false);
                        } else {
                            user.setStatus(true);
                        }
                        accountController.updateAccount(user);
                        AccountTableModel model = new AccountTableModel(accountController.getAllAccounts());
                        tableAccount.setModel(model);
                    }
                }
            }
        });
        tableAccount.addContainerListener(new ContainerAdapter() {
        });
    }

    private void showFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();


            try {
                pictureData = Files.readAllBytes(selectedFile.toPath());
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error reading the image file", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Display the selected image in the JLabel
            displayImage(selectedFile);
        }
    }

    private void displayImage(File file) {
        ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
        Image image = imageIcon.getImage().getScaledInstance(pictureUpload.getWidth(), pictureUpload.getHeight(), Image.SCALE_SMOOTH);
        pictureUpload.setIcon(new ImageIcon(image));
    }

    private void createNewAccount() {
        String username = textFieldUsername.getText();
        String password = new String(passwordField.getPassword());
        String confirm = new String(passwordFieldConfirm.getPassword());
        String role = (String) comboBoxRole.getSelectedItem();
        String name = textFieldName.getText();
        int age = 0;
        if (textFieldAge.getText().isEmpty() || textFieldAge.getText() == null) {
            JOptionPane.showMessageDialog(this, "Please submit enough valid information", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else {
            try {
                age = Integer.parseInt(textFieldAge.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Not valid age", "Error", JOptionPane.ERROR_MESSAGE);
                textFieldAge.setText("");
                return;
            }
        }
        String phoneNumber = textFieldPhoneNumber.getText();

        if (username.isEmpty() || username == null || password.isEmpty() || password == null ||
                confirm.isEmpty() || confirm == null || name.isEmpty() || name == null ||
                phoneNumber.isEmpty() || phoneNumber == null) {
            JOptionPane.showMessageDialog(this, "Please submit enough valid information", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        if (!confirm.equals(password)) {
            JOptionPane.showMessageDialog(this, "Password and Confirm Password are not match", "Error", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            passwordFieldConfirm.setText("");
            passwordField.requestFocus();
            return;
        }

        Account existingAccount = accountController.getAccountByUsername(username);
        if (existingAccount != null) {
            JOptionPane.showMessageDialog(this, "Username already exists. Please choose another username.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Account newAccount = new Account(username, password, role, pictureData, name, age, phoneNumber, true);

        Integer accountId = accountController.addAccount(newAccount);

        if (accountId != null) {
            JOptionPane.showMessageDialog(this, "Account created successfully. Account ID: " + accountId);
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create account.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        AccountTableModel model = new AccountTableModel(accountController.getAllAccounts());
        tableAccount.setModel(model);
    }

    private void clearForm() {
        textFieldUsername.setText("");
        passwordField.setText("");
        passwordFieldConfirm.setText("");
        comboBoxRole.setSelectedIndex(0);
        textFieldName.setText("");
        textFieldAge.setText("");
        textFieldPhoneNumber.setText("");
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource("static/images/add-icon.png");
            BufferedImage img = ImageIO.read(resource);
            pictureUpload.setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
