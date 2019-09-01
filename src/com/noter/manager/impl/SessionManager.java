package com.noter.manager.impl;

import com.noter.Noter;
import com.noter.models.Account;
import com.noter.scenes.Scenes;

/**
 * @author Willian Gois (github/willgoix)
 */
public class SessionManager {

    private Account loggedAccount;

    public SessionManager() {

    }

    public void startSessionWithNewAccount(Account account) {
        setLoggedAccount(account);

        Noter.getNoter().start();
        Noter.getNoter().getSceneController().changeScene(Scenes.DASHBOARD);
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

