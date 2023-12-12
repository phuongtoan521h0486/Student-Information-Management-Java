package org.thd.Models;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryTableModel extends AbstractTableModel {
    private List<LoginHistory> loginHistoryList;
    private List<Account> accountList;
    private final String[] columnNames = {"Username", "Role", "Login Time"};

    public HistoryTableModel(List<LoginHistory> loginHistoryList, List<Account> accountList) {
        this.loginHistoryList = loginHistoryList;
        this.accountList = accountList;
    }

    @Override
    public int getRowCount() {
        return loginHistoryList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LoginHistory loginHistory = loginHistoryList.get(rowIndex);
        Account account = getAccountById(loginHistory.getAccountId());
        switch (columnIndex) {
            case 0:
                return (account != null) ? account.getUsername() : "N/A";

            case 1:
                return (account != null) ? account.getRole() : "N/A";

            case 2:
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, hh:mm:ss a");
                return dateFormat.format(loginHistory.getLoginTime());

            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // Helper method to get an account by ID
    private Account getAccountById(int accountId) {
        for (Account account : accountList) {
            if (account.getId() == accountId) {
                return account;
            }
        }
        return null;  // Account not found
    }
}

