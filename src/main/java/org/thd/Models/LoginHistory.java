package org.thd.Models;

import java.util.Date;

public class LoginHistory {
    private int id;
    private Date loginTime;
    private int accountId;

    public LoginHistory() {
    }

    public LoginHistory(int id, Date loginTime, int accountId) {
        this.id = id;
        this.loginTime = loginTime;
        this.accountId = accountId;
    }

    public LoginHistory(Date loginTime, int accountId) {
        this.loginTime = loginTime;
        this.accountId = accountId;
    }

    public LoginHistory(int accountId) {
        this.accountId = accountId;
        this.loginTime = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "LoginHistory{" +
                "id=" + id +
                ", loginTime=" + loginTime +
                ", accountId=" + accountId +
                '}';
    }
}
