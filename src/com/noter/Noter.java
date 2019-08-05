package com.noter;

import com.noter.manager.impl.AccountManager;
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

    public Noter(Stage primaryStage) {
        this.sceneController = new SceneController(primaryStage, Scenes.AUTHENTICATION);
        this.storage = new NoterStorage("", 0, "", "", "");

        this.accountManager = new AccountManager(storage);

        noter = this;
    }

    public SceneController getSceneController() {
        return sceneController;
    }

    public NoterStorage getStorage() {
        return storage;
    }
}
