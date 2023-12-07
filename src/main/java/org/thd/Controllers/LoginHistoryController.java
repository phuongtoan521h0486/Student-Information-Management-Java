package org.thd.Controllers;

import org.thd.DAO.LoginHistoryDAO;
import org.thd.Models.LoginHistory;

import java.util.List;

public class LoginHistoryController {
    private LoginHistoryDAO loginHistoryDAO;

    public LoginHistoryController() {
        this.loginHistoryDAO = new LoginHistoryDAO();
    }

    // Add a login history record
    public int addLoginHistory(LoginHistory loginHistory) {
        return loginHistoryDAO.add(loginHistory);
    }

    public List<LoginHistory> getAllLoginHistory() {
        return loginHistoryDAO.readAll();
    }

    public LoginHistory getLoginHistoryById(int id) {
        return loginHistoryDAO.read(id);
    }

    public boolean updateLoginHistory(LoginHistory loginHistory) {
        return loginHistoryDAO.update(loginHistory);
    }

    public boolean deleteLoginHistoryById(int id) {
        return loginHistoryDAO.delete(id);
    }
}
