package org.thd.Models;

import org.thd.DAO.LoginHistoryDAO;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryDetailTableModel extends AbstractTableModel {
    private List<LoginHistory> loginHistoryList;
    private Account account;
    private final String[] columnNames = {"Day of the week, Month, Day of the month, Year, Hour, Minutes, Seconds"};

    public HistoryDetailTableModel(Account account) {
        this.account = account;
        LoginHistoryDAO loginHistoryDAO = new LoginHistoryDAO();
        loginHistoryList = loginHistoryDAO.readAllByAccountId(account.getId());
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
        String pattern = "EEEE, MMMM d, yyyy, hh:mm:ss a";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        switch (columnIndex) {
            case 0:
                return dateFormat.format(loginHistory.getLoginTime());
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

}
