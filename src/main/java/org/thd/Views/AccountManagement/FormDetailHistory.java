package org.thd.Views.AccountManagement;

import org.thd.Controllers.AccountController;
import org.thd.Models.Account;
import org.thd.Models.HistoryDetailTableModel;

import javax.swing.*;

public class FormDetailHistory extends JFrame{
    private JPanel panelMain;
    private JLabel labelNameNRole;
    private JTable tableHistoryDetail;

    private AccountController accountController;

    public FormDetailHistory(String name) {
        setTitle("Login History Detail Form");
        setSize(500, 500);
        setLocationRelativeTo(null);

        accountController = new AccountController();
        Account account = accountController.getAccountByName(name);

        labelNameNRole.setText(account.getName() + ": " + account.getRole());

        HistoryDetailTableModel historyDetailTableModel = new HistoryDetailTableModel(account);
        tableHistoryDetail.setModel(historyDetailTableModel);
        add(panelMain);
    }
}
