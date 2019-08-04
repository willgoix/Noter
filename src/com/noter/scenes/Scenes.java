package com.noter.scenes;

/**
 * @author Willian Gois (github/willgoix)
 */
public enum Scenes {

    AUTHENTICATION("auth/auth.fxml", 800, 600),
    REGISTER("register/register.fxml", 800, 600),
    FORGOT_PASSWORD("forgot_password.fxml", 800, 600);

    private String path;
    private int width;
    private int height;

    Scenes(String path, int width, int height) {
        this.path = path;
        this.width = width;
        this.height = height;
    }

    public String getPath() {
        return path;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
