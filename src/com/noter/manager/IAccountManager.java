package com.noter.manager;

import com.noter.models.Account;

import java.util.Map;

/**
 * @author Willian Gois (github/willgoix)
 */
public interface IAccountManager {

    void addAccount(Account account);

    void updateAccount(Account account);

    void removeAccount(Account account);

    Map<String, Account> getAccounts();
}
