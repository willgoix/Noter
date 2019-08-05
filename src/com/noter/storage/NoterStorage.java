package com.noter.storage;

/**
 * @author Willian Gois (github/willgoix)
 */
public class NoterStorage extends Database {

    public final String TABLE_ACCOUNTS = "accounts";

    public NoterStorage(String host, Integer port, String database, String user, String password) {
        super(host, port, database, user, password);

        query("CREATE TABLE IF NOT EXISTS " + TABLE_ACCOUNTS + "("
                + "username VARCHAR(32) PRIMARY KEY NOT NULL,"
                + "email VARCHAR(50),"
                + "password VARCHAR(100));");
    }
}
