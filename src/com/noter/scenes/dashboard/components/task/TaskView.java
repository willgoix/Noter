package com.noter.scenes.dashboard.components.task;

import com.noter.scenes.dashboard.components.taskDetails.TaskDetailsView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TaskView extends AnchorPane {

    @FXML
    private Label taskName;

    @FXML
    private Label taskDateCreation;

    @FXML
    private MaterialDesignIconView starFavorite;

    private SplitPane parent;

    public TaskView(SplitPane parent) {
        super();
        this.parent = parent;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskView.fxml"));
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
    private void handleOpenDetails() {
        VBox tasksBox = (VBox) parent.getItems().get(0);

        tasksBox.getChildren().forEach(node -> {
            TaskView taskView = (TaskView) node;
            taskView.setPrefWidth(taskView.getPrefWidth() / 2);
        });

        if (parent.getItems().size() == 2) {
            parent.getItems().remove(1);
        } else {
            TaskDetailsView taskDetailsView = new TaskDetailsView(parent);
            taskDetailsView.setTaskName(getTaskName());
            taskDetailsView.setFavorited(Boolean.valueOf(starFavorite.getId()));

            parent.getItems().add(taskDetailsView);
        }
    }

    @FXML
    private void handleComplete() {
    }

    @FXML
    private void handleFavorite() {
    }
}