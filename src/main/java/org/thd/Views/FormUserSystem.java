package org.thd.Views;

import org.thd.Controllers.AccountController;
import org.thd.Models.TableAccountModel;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormUserSystem extends JFrame{
    private JPanel panelMain;
    private JTable tableListUser;
    private JButton buttonAdd;
    private JScrollPane myScroll;
    private JButton buttonEdit;
    private JButton buttonDelete;
    private JButton buttonRefesh;
    private JButton buttonHistory;

    private AccountController accountController;

    public FormUserSystem() {
        setTitle("User System Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 720);
        setLocationRelativeTo(null);

        accountController = new AccountController();
        TableAccountModel model = new TableAccountModel(accountController.getAllAccounts());
        tableListUser.setModel(model);

        tableListUser.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());

        add(panelMain);
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // mo form create account len
            }
        });
    }
}

 class ImageRenderer extends DefaultTableCellRenderer {
     private static final int ICON_WIDTH = 64;
     private static final int ICON_HEIGHT = 64;

     @Override
     public Component getTableCellRendererComponent(
             JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         JLabel label = new JLabel();

         if (value instanceof ImageIcon) {
             ImageIcon originalIcon = (ImageIcon) value;
             ImageIcon resizedIcon = resizeIcon(originalIcon, ICON_WIDTH, ICON_HEIGHT);
             label.setIcon(resizedIcon);
         }

         return label;
     }

     private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
         Image img = icon.getImage();
         Image newImg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
         return new ImageIcon(newImg);
     }
 }
