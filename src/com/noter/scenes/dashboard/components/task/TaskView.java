package com.noter.scenes.dashboard.components.task;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TaskView extends AnchorPane {

    @FXML
    private Label taskName;

    @FXML
    private Label taskDateCreation;

    public TaskView() {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("taskView.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setTaskName(String name) {
        taskNameProperty().set(name);
    }

    public String getTaskName() {
        return taskNameProperty().get();
    }

    public StringProperty taskNameProperty() {
        return taskName.textProperty();
    }

    public void setTaskDateCreation(String dateCreation) {
        taskDateCreationProperty().set(dateCreation);
    }

    public String getTaskDateCreation() {
        return taskDateCreationProperty().get();
    }

    public StringProperty taskDateCreationProperty() {
        return taskDateCreation.textProperty();
    }

    @FXML
    private void handleComplete() { }
}