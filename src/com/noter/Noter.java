package com.noter;

import com.noter.manager.impl.AccountManager;
import com.noter.manager.impl.AuthManager;
import com.noter.manager.impl.ProjectManager;
import com.noter.manager.impl.SessionManager;
import com.noter.scenes.SceneController;
import com.noter.scenes.Scenes;
import com.noter.storage.NoterStorage;
import javafx.stage.Stage;

/**
 * @author Willian Gois (github/willgoix)
 *
 * Design Pattern:
 *  Builder
 *  Factory
 */
public class Noter {

    private static Noter noter;

    public static Noter getNoter() {
        return noter;
    }


    private SceneController sceneController;
    private NoterStorage storage;

    private AccountManager accountManager;
    private AuthManager authManager;
    private SessionManager sessionManager;
    private ProjectManager projectManager;

    public Noter(Stage primaryStage) {
        noter = this;

        this.sceneController = new SceneController(primaryStage, Scenes.AUTHENTICATION);
        this.load();
    }

    public void load() {
        this.storage = new NoterStorage("localhost", 3306, "noter", "root", "admin");

        this.accountManager = new AccountManager(storage);
        this.authManager = new AuthManager(this.accountManager);
        this.sessionManager = new SessionManager();
        this.projectManager = new ProjectManager(storage);
    }

    public void start() {
        this.projectManager.start(sessionManager.getLoggedAccount());
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public AuthManager getAuthManager() {
        return authManager;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public SceneController getSceneController() {
        return sceneController;
    }

    public ProjectManager getProjectManager() {
        return projectManager;
    }

    public NoterStorage getStorage() {
        return storage;
    }
}
