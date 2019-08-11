package com.noter.scenes;

import com.noter.Noter;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

    private Stage stage;
    private Scene actualScene;
    private Scene previousScene;

    public SceneController(Stage stage, Scenes startScene) {
        this.stage = stage;

        /* Serviço para mostrar tela Splash enquanto o aplicativo está carregando */
        Service<Boolean> splashService = new Service<Boolean>() {
            @Override
            public void start() {
                super.start();
                changeScene(Scenes.SPLASH);
            }

            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() {
                        Noter.getNoter().load();
                        return true;
                    }
                };
            }

            @Override
            protected void succeeded() {
                changeScene(startScene);
            }
        };

        splashService.start();
    }

    public void changeScene(Scenes scene) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scene.getPath()));
            Parent rootPane = loader.load();
            Scene sceneFx = new Scene(rootPane, scene.getWidth(), scene.getHeight());

            this.previousScene = this.actualScene;
            this.actualScene = sceneFx;

            loadScene(sceneFx);
        } catch (Exception e) {
            System.out.println("Erro ao trocar de tela: ");
            e.printStackTrace();
            //TODO: Do log.
        }
    }

    public void goBack() {
        try {
            if (previousScene != null) {
                this.actualScene = previousScene;

                loadScene(previousScene);
            }
        } catch (Exception e) {
            System.out.println("Erro ao voltar de tela: ");
            e.printStackTrace();
            //TODO: Do log.
        }
    }

    private void loadScene(Scene scene) throws Exception {
        this.stage.setScene(scene);
        this.stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getActualScene() {
        return actualScene;
    }

    public Scene getPreviousScene() {
        return previousScene;
    }
}
