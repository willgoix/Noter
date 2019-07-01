package com.noter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("scenes/auth/auth.fxml"));

        primaryStage.initStyle(StageStyle.UNIFIED); //TRANSPARENT
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("Noter - v0.1");
        primaryStage.setScene(new Scene(root, 680, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
