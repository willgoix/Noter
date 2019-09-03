package com.noter.scenes.dashboard.components.taskDetails;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * @author Willian Gois (github/willgoix)
 */
public class TaskDetailsView extends AnchorPane {

    @FXML
    private Label taskName;

    @FXML
    private Label taskDescription;

    @FXML
    private MaterialDesignIconView starFavorite;

    private SplitPane parent;

    public TaskDetailsView(SplitPane parent) {
        super();
        this.parent = parent;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskDetailsView.fxml"));
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

    public void setTaskDescription(String description) {
        taskDescriptionProperty().set(description);
    }

    public String getTaskDescription() {
        return taskDescriptionProperty().get();
    }

    public StringProperty taskDescriptionProperty() {
        return taskDescription.textProperty();
    }

    public void setFavorited(boolean favorited) {
        starFavorite.setId(Boolean.toString(favorited));
        starFavorite.setIcon(favorited ? MaterialDesignIcon.STAR : MaterialDesignIcon.STAR_OUTLINE);
    }

    @FXML
    private void handleCompleted() {

    }

    @FXML
    private void handleFavorite() {
        if (starFavorite.getId().equals("true")) {
            starFavorite.setIcon(MaterialDesignIcon.STAR_OUTLINE);
        } else {
            starFavorite.setIcon(MaterialDesignIcon.STAR);
        }
    }
}
