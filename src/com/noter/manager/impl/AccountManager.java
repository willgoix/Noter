package com.noter.manager.impl;

import com.noter.manager.IAccountManager;
import com.noter.models.Account;
import com.noter.storage.AccountStorage;
import com.noter.storage.NoterStorage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Willian Gois (github/willgoix)
 */
public class AccountManager implements IAccountManager {

    private final AccountStorage storage;

    private HashMap<String, Account> accounts;

    public AccountManager(NoterStorage storage) {
        this.storage = new AccountStorage(storage);
        this.accounts = null;
    }

    @Override
    public void addAccount(Account account) {
        this.accounts.put(account.getUsername(), account);
    }

    @Override
    public void updateAccount(Account account) {
        addAccount(account);
    }

    @Override
    public void removeAccount(Account account) {
        this.accounts.remove(account.getUsername());
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }
}
