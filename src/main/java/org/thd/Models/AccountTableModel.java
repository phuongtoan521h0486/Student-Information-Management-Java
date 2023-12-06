package org.thd.Models;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AccountTableModel extends AbstractTableModel {

    private List<Account> accountList;
    private String[] columnNames = {"ID", "Username", "Name", "Status"};

    public AccountTableModel(List<Account> accountList) {
        this.accountList = accountList;
    }
    @Override
    public int getRowCount() {
        return accountList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Account account = accountList.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return account.getId();
            case 1:
                return account.getUsername();
            case 2:
                return account.getName();
            case 3:
                return account.isStatus() ? "Normal": "Locked";
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
