package com.noter.storage;

import com.noter.manager.IAccountManager;
import com.noter.models.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Willian Gois (github/willgoix)
 */
public class AccountStorage implements IAccountManager {

    private final NoterStorage storage;

    public AccountStorage(NoterStorage storage) {
        this.storage = storage;
    }

    @Override
    public void addAccount(Account account) {
        storage.query("INSERT INTO ? (username, email, password) VALUES (?, ?, ?);",
                storage.TABLE_ACCOUNTS,
                account.getUsername(),
                account.getEmail(),
                account.getPassword());
    }

    @Override
    public void updateAccount(Account account) {
        storage.query("UPDATE TABLE ? SET email = ?, password = ? WHERE username = ?;",
                storage.TABLE_ACCOUNTS,
                account.getEmail(),
                account.getPassword(),
                account.getUsername());
    }

    @Override
    public void removeAccount(Account account) {
        storage.query("DELETE FROM ? WHERE username = ?;",
                storage.TABLE_ACCOUNTS,
                account.getUsername());
    }

    @Override
    public Map<String, Account> getAccounts() {
        HashMap<String, Account> accounts = new HashMap<>();

        try (PreparedStatement statement = storage.getConnection().prepareStatement("SELECT * FROM ?;")) {
            statement.setString(1, storage.TABLE_ACCOUNTS);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Account account = new Account(
                        result.getString("username"),
                        result.getString("email"),
                        result.getString("password")
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
