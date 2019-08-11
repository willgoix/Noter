package com.noter.models;

/**
 * @author Willian Gois (github/willgoix)
 */
public class Account {

    private String username;
    private String email;
    private String password;
    private String passwordSalt;

    public Account(String username, String email, String password, String passwordSalt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordSalt = passwordSalt;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }
}
