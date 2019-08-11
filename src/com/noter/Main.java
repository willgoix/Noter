package com.noter;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Willian Gois (github/willgoix)
 */
public class Main extends Application {

    private static Main main;

    public static Main getMain() {
        return main;
    }

    @Override
    public void start(Stage primaryStage) {
        this.main = this;

        primaryStage.initStyle(StageStyle.UNIFIED); //TRANSPARENT
        primaryStage.setResizable(false);
        primaryStage.setTitle("Noter - v0.1");

        new Noter(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
