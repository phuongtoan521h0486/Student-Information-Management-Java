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
    private JButton buttonHistory;

    private AccountController accountController;

    public FormUserSystem() {
        setTitle("User System Form");
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

        buttonHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableListUser.getSelectedRow();
                String name = selectedRow != -1 ? (String) tableListUser.getValueAt(selectedRow, 2) : null;
                if (name == null) {
                    JOptionPane.showMessageDialog(null, "Please select a user", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                new FormDetailHistory(name).setVisible(true);
            }
        });
    }
}

