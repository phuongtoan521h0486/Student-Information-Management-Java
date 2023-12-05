package org.thd.Views;

import org.thd.Controllers.AccountController;
import org.thd.Models.Account;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class FormMain extends JFrame{
    private JPanel panelMain;
    private JLabel image;
    private JLabel displayName;
    private JButton buttonHome;
    private JButton buttonUser;
    private JButton buttonStudent;

    private Account user;
    private AccountController accountController;

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
    }

}
