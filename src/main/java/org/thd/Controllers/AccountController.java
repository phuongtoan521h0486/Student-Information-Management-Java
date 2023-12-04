package org.thd.Controllers;

import org.mindrot.jbcrypt.BCrypt;
import org.thd.DAO.AccountDAO;
import org.thd.Models.Account;

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

    public Account getAccountById(Integer accountId) {
        return accountDAO.read(accountId);
    }

    public boolean updateAccount(Account account) {
        return accountDAO.update(account);
    }

    public boolean deleteAccount(Integer accountId) {
        return accountDAO.delete(accountId);
    }

    public Account getAccountByUsername(String username) {
        return accountDAO.getAccountByUsername(username);
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
