package org.thd.Views.Home;

import org.thd.Controllers.AccountController;
import org.thd.Controllers.LoginHistoryController;
import org.thd.Models.Account;
import org.thd.Models.HistoryTableModel;
import org.thd.Models.LoginHistory;
import org.thd.Views.AccountManagement.FormUserSystem;
import org.thd.Views.StudentManagement.FormStudentManagement;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FormMain extends JFrame{
    private JPanel panelMain;
    private JLabel image;
    private JLabel displayName;
    private JButton buttonUser;
    private JButton buttonStudent;
    private JButton logoutButton;
    private JTable tableLoginHistory;
    private JScrollPane historyLogin;

    private Account user;
    private AccountController accountController;
    private LoginHistoryController loginHistoryController;
    private byte[] pictureData;


    public FormMain(Account user) {
        accountController = new AccountController();
        loginHistoryController = new LoginHistoryController();

        this.user = user;

        displayName.setText(user.getName());
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(user.getPicture()));
            image.setIcon(new ImageIcon(img.getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!user.getRole().equals("Admin")) {
            buttonUser.setVisible(false);
            historyLogin.setVisible(false);
        }

        setTitle("Main Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 720);
        setLocationRelativeTo(null);
        add(panelMain);

        List<LoginHistory> loginHistoryList = loginHistoryController.getAllLoginHistory();
        List<Account> accountList = accountController.getAllAccounts();

        tableLoginHistory.setModel(new HistoryTableModel(loginHistoryList, accountList));

        buttonUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormUserSystem().setVisible(true);
            }
        });
        image.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showFileChooser();
            }
        });
        buttonStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormStudentManagement(user).setVisible(true);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(FormMain.this, "Do you want to log out ?");
                if (confirm == JOptionPane.YES_OPTION) {
                    new FormLogin().setVisible(true);
                    dispose();
                }
            }
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

            displayImage(selectedFile);
            if (JOptionPane.showConfirmDialog(this,
                    "Do you want to apply this picture?", "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                accountController.updatePicture(user.getId(), pictureData);

            }
            else {
                try {
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(user.getPicture()));
                    image.setIcon(new ImageIcon(img.getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private void displayImage(File file) {
        ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
        Image imageNew = imageIcon.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_SMOOTH);
        image.setIcon(new ImageIcon(imageNew));

    }

}
