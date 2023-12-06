package org.thd.Models;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserTableModel extends AbstractTableModel {
    private List<Account> accountList;
    private String[] columnNames = {"Profile Picture", "Role", "Name", "Age", "Phone Number", "Status"};

    public UserTableModel(List<Account> accountList) {
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
                return byteArrayToImageIcon(account.getPicture());
            case 1:
                return account.getRole();
            case 2:
                return account.getName();
            case 3:
                return account.getAge();
            case 4:
                return account.getPhoneNumber();
            case 5:
                return account.isStatus() ? "Normal": "Locked";
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    private ImageIcon byteArrayToImageIcon(byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            return new ImageIcon(bytes);
        } else {
            return null;
        }
    }
}
