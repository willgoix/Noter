package com.noter.storage;

import com.noter.models.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Willian Gois (github/willgoix)
 */
public class TokenStorage {

    private final NoterStorage storage;

    public TokenStorage(NoterStorage storage) {
        this.storage = storage;

        storage.query("CREATE TABLE IF NOT EXISTS "+ storage.TABLE_RESET_PASSWORD_TOKENS +" (" +
                        "account VARCHAR(16) PRIMARY KEY, " +
                        "token VARCHAR(100) UNIQUE NOT NULL, " +
                        "created_at LONG NOT NULL, " +
                        "FOREIGN KEY (account) REFERENCES accounts(username));");
    }

    public void addToken(Account account, String token, long createdAt) {
        storage.query("INSERT INTO "+ storage.TABLE_RESET_PASSWORD_TOKENS + " VALUES (?, ?, ?);",
                account.getUsername(),
                token,
                createdAt);
    }

    public boolean checkToken(Account account, String token, long timestampToCompare, long maxMillisToExpire) {
        try (PreparedStatement statement = storage.getConnection().prepareStatement("SELECT * FROM "+ storage.TABLE_RESET_PASSWORD_TOKENS +" WHERE account = ? AND token = ?;")) {
            statement.setString(1, account.getUsername());
            statement.setString(2, token);

            ResultSet result = statement.executeQuery();
            result.next();

            return result.getLong("created_at") + maxMillisToExpire > timestampToCompare;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
