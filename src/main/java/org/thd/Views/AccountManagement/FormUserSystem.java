package org.thd.Views.AccountManagement;

import org.thd.Components.ImageRenderer;
import org.thd.Controllers.AccountController;
import org.thd.Models.AccountTableModel;
import org.thd.Models.UserTableModel;

import javax.swing.*;
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
    private JButton buttonBack;

    private AccountController accountController;

    public FormUserSystem() {
        setTitle("User System Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 720);
        setLocationRelativeTo(null);

        accountController = new AccountController();
        UserTableModel model = new UserTableModel(accountController.getAllAccounts());
        tableListUser.setModel(model);

        tableListUser.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer(64, 64));

        add(panelMain);


        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormAccountCRUD().setVisible(true);
                dispose();
            }
        });
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                    implement below need an id column to delete user
                 */

//                int row = tableListUser.getSelectedRow();
//                if (row == -1) {
//                    JOptionPane.showMessageDialog(FormUserSystem.this, "Please chose an account you want to delete", "Error", JOptionPane.ERROR_MESSAGE);
//                } else {
//                    int confirm = JOptionPane.showConfirmDialog(FormUserSystem.this, "You want to delete this user");
//                    if (confirm == JOptionPane.YES_OPTION) {
//                        int userID = Integer.parseInt(String.valueOf(tableListUser.getValueAt(row, 0)));
//                        accountController.deleteAccount(userID);
//                        UserTableModel model = new UserTableModel(accountController.getAllAccounts());
//                        tableListUser.setModel(model);
//                    }
//                }
            }
        });
    }
}

