package com.noter.manager.impl;

import com.noter.models.Account;
import com.noter.storage.NoterStorage;
import com.noter.storage.TokenStorage;
import com.noter.util.PasswordUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author Willian Gois (github/willgoix)
 */
public class TokenManager {

    private final TokenStorage storage;

    public TokenManager(NoterStorage noterStorage) {
        this.storage = new TokenStorage(noterStorage);
    }

    public String generateToken(Account account) {
        String token = PasswordUtils.getSalt(10);
        long createdAt = System.currentTimeMillis();

        storage.addToken(account, token, createdAt);
        return token;
    }

    public boolean checkToken(Account account, String token) {
        return storage.checkToken(account, token, System.currentTimeMillis(), TimeUnit.HOURS.toMillis(1));
    }
}
