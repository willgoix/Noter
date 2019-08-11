package com.noter.manager.impl;

import com.noter.manager.IAccountManager;
import com.noter.models.Account;
import com.noter.storage.AccountStorage;
import com.noter.storage.NoterStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Willian Gois (github/willgoix)
 */
public class AccountManager implements IAccountManager {

    private final AccountStorage storage;

    private HashMap<String, Account> accounts;

    public AccountManager(NoterStorage storage) {
        this.storage = new AccountStorage(storage);
        this.accounts = new HashMap<>();

        this.accounts.putAll(this.storage.getAccounts());
    }

    public void addAccount(Account account) {
        this.accounts.put(account.getUsername(), account);
    }

    public void updateAccount(Account account) {
        addAccount(account);
    }

    public void removeAccount(Account account) {
        this.accounts.remove(account.getUsername());
    }

    public Optional<Account> getAccountByUsername(String username) {
        return Optional.ofNullable(this.accounts.get(username));
    }

    public Optional<Account> getAccountByEmail(String email) {
        return this.accounts.values().stream().filter(account -> account.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public AccountStorage getStorage() {
        return storage;
    }
}
