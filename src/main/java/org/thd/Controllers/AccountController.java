package org.thd.Controllers;

import org.mindrot.jbcrypt.BCrypt;
import org.thd.DAO.AccountDAO;
import org.thd.Models.Account;

import java.sql.ResultSet;
import java.util.List;

public class AccountController {
    private AccountDAO accountDAO;

    public AccountController() {
        this.accountDAO = new AccountDAO();
    }

    public Integer addAccount(Account account) {
        return accountDAO.add(account);
    }

    public List<Account> getAllAccounts() {
        return accountDAO.readAll();
    }

    public ResultSet getAllAccountsResultSet() {
        return accountDAO.readAllResultSet();
    }

    public Account getAccountById(Integer accountId) {
        return accountDAO.read(accountId);
    }

    public ResultSet getAccountByIdResultSet(Integer accountId) {
        return accountDAO.readResultset(accountId);
    }

    public boolean updateAccount(Account account) {
        return accountDAO.update(account);
    }
    public boolean updateAccountNoPassword(Account account) {
        return accountDAO.updateNoPassword(account);
    }

    public boolean deleteAccount(Integer accountId) {
        return accountDAO.delete(accountId);
    }

    public Account getAccountByUsername(String username) {
        return accountDAO.getAccountByUsername(username);
    }

    public Account getAccountByName(String name)  {
        return accountDAO.getAccountByName(name);
    }

    public boolean updatePicture(Integer id, byte[] newPicture) {
        return accountDAO.updatePicture(id, newPicture);
    }

    public Account authenticateUser(String username, String password) {
        Account authenticatedAccount = accountDAO.getAccountByUsername(username);

        if (authenticatedAccount != null && checkPassword(password, authenticatedAccount.getPassword())) {
            return authenticatedAccount;
        }

        return null;
    }
    private boolean checkPassword(String enteredPassword, String hashedPassword) {
        return BCrypt.checkpw(enteredPassword, hashedPassword);
    }
}
