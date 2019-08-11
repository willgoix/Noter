package com.noter.storage;

import com.noter.manager.IAccountManager;
import com.noter.models.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Willian Gois (github/willgoix)
 */
public class AccountStorage implements IAccountManager {

    private final NoterStorage storage;

    public AccountStorage(NoterStorage storage) {
        this.storage = storage;

        storage.query("CREATE TABLE IF NOT EXISTS "+ storage.TABLE_ACCOUNTS +" (" +
                        "username VARCHAR(16) PRIMARY KEY, " +
                        "email VARCHAR(36) NOT NULL UNIQUE, " +
                        "password VARCHAR(100) NOT NULL," +
                        "passwordSalt VARCHAR(30) NOT NULL);");
    }

    public void addAccount(Account account) {
        storage.query("INSERT INTO "+ storage.TABLE_ACCOUNTS + " (username, email, password, passwordSalt) VALUES (?, ?, ?, ?);",
                account.getUsername(),
                account.getEmail(),
                account.getPassword(),
                account.getPasswordSalt());
    }

    public void updateAccount(Account account) {
        storage.query("UPDATE TABLE " + storage.TABLE_ACCOUNTS + " SET email = ?, password = ? WHERE username = ?;",
                account.getEmail(),
                account.getPassword(),
                account.getUsername());
    }

    public void removeAccount(Account account) {
        storage.query("DELETE FROM " + storage.TABLE_ACCOUNTS + " WHERE username = ?;",
                account.getUsername());
    }

    public Map<String, Account> getAccounts() {
        HashMap<String, Account> accounts = new HashMap<>();

        try (PreparedStatement statement = storage.getConnection().prepareStatement("SELECT * FROM " + storage.TABLE_ACCOUNTS + ";")) {
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Account account = new Account(
                        result.getString("username"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getString("passwordSalt")
                );

                accounts.put(account.getUsername(), account);
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }
}
