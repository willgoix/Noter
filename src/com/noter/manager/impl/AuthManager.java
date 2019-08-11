package com.noter.manager.impl;

import com.noter.models.Account;
import com.noter.util.PasswordUtils;

import java.util.Optional;

/**
 * @author Willian Gois (github/willgoix)
 */
public class AuthManager {

    private final AccountManager accountManager;

    public AuthManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public Optional<Account> auth(String username, String password) {
        Optional<Account> accountOptional = accountManager.getAccountByUsername(username);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            boolean ok = PasswordUtils.verifyUserPassword(password, account.getPassword(), account.getPasswordSalt());

            if (ok) return Optional.of(account);
        }

        return Optional.empty();
    }

    public Account register(String username, String email, String password) {
        String salt = PasswordUtils.getSalt(30);
        String securePassword = PasswordUtils.generateSecurePassword(password, salt);

        Account account = new Account(username, email, securePassword, salt);

        accountManager.addAccount(account);
        accountManager.getStorage().addAccount(account);

        return account;
    }
}
