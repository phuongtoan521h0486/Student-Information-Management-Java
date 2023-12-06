package org.thd.Views.Home;

import org.thd.Controllers.AccountController;
import org.thd.Models.Account;
import org.thd.Views.AccountManagement.FormUserSystem;

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

public class FormMain extends JFrame{
    private JPanel panelMain;
    private JLabel image;
    private JLabel displayName;
    private JButton buttonHome;
    private JButton buttonUser;
    private JButton buttonStudent;
    private JButton logoutButton;
    private JTable tableHistoryLogin;

    private Account user;
    private AccountController accountController;
    private byte[] pictureData;

    public FormMain(Account user) {
        accountController = new AccountController();
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
        }

        setTitle("Main Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 720);
        setLocationRelativeTo(null);
        add(panelMain);

        buttonUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormUserSystem().setVisible(true);
                dispose();
            }
        });
        image.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showFileChooser();
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
