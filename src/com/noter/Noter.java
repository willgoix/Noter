package com.noter;

import com.noter.scenes.SceneController;
import com.noter.scenes.Scenes;
import javafx.stage.Stage;

/**
 * @author Willian Gois (github/willgoix)
 */
public class Noter {

    private static Noter noter;

    public static Noter getNoter() {
        return noter;
    }


    private SceneController sceneController;

    public Noter(Stage primaryStage) {
        this.sceneController = new SceneController(primaryStage, Scenes.AUTHENTICATION);

        noter = this;
    }

    public SceneController getSceneController() {
        return sceneController;
    }
}
