package com.noter.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    private Stage stage;
    private Scene actualScene;

    public SceneController(Stage stage, Scenes startScene) {
        this.stage = stage;
        changeScene(startScene);
    }

    public void changeScene(Scenes scene) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scene.getPath()));
            Parent rootPane = loader.load();
            Scene sceneFx = new Scene(rootPane, scene.getWidth(), scene.getHeight());

            this.actualScene = sceneFx;

            this.stage.setScene(sceneFx);
            this.stage.show();
        } catch (IOException e) {
            //TODO: Do log.
        }
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getActualScene() {
        return actualScene;
    }
}
