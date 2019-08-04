package com.noter;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UNIFIED); //TRANSPARENT
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("Noter - v0.1");

        new Noter(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
