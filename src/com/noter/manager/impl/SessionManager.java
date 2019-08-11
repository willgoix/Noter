package com.noter.manager.impl;

import com.noter.models.Account;

/**
 * @author Willian Gois (github/willgoix)
 */
public class SessionManager {

    private Account loggedAccount;

    public SessionManager() {

    }

    public void startSessionWithNewAccount(Account account) {
        setLoggedAccount(account);

        //TODO: Load projects and tasks to display
    }

    public void startSession() {
        startSessionWithNewAccount(null);
    }

    public void setLoggedAccount(Account loggedAccount) {
        this.loggedAccount = loggedAccount;
    }

    public Account getLoggedAccount() {
        return loggedAccount;
    }
}

