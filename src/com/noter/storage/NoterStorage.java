package com.noter.storage;

/**
 * @author Willian Gois (github/willgoix)
 */
public class NoterStorage extends Database {

    private final String DATABASE = "noter";
    public final String TABLE_ACCOUNTS = "accounts";
    public final String TABLE_PROJECTS = "projects";
    public final String TABLE_PROJECT_MEMBERS = "project_members";
    public final String TABLE_TASKS = "tasks";
    public final String TABLE_TASKS_MEDIAS = "tasks_medias";
    public final String TABLE_RESET_PASSWORD_TOKENS = "reset_password_tokens";

    public NoterStorage(String host, Integer port, String database, String user, String password) {
        super(host, port, database, user, password);

        query("CREATE DATABASE IF NOT EXISTS " + DATABASE);
    }
}
