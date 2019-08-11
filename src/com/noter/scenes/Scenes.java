package com.noter.scenes;

/**
 * @author Willian Gois (github/willgoix)
 */
public enum Scenes {

    SPLASH("splash/splash.fxml", 680, 500),
    AUTHENTICATION("auth/auth.fxml", 680, 500),
    REGISTER("register/register.fxml", 680, 500),
    FORGOT_PASSWORD("forgotPassword/forgot_password.fxml", 680, 500);

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
