package com.noter.scenes.dashboard.components.project;

import com.noter.models.ProjectMemberRole;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * @author Willian Gois (github/willgoix)
 */
public class ProjectView extends AnchorPane {

    public static int NAME_NODE_INDEX = 0;

    @FXML
    private HBox container;

    @FXML
    private Label projectName;

    public ProjectView() {
        super();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProjectView.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setProjectName(String name) {
        projectNameProperty().set(name);
    }

    public String getProjectName() {
        return projectNameProperty().get();
    }

    public StringProperty projectNameProperty() {
        return projectName.textProperty();
    }

    public void setAccountRole(ProjectMemberRole role) {
        if (role == ProjectMemberRole.AUTHOR) {
            container.setBackground(new Background(new BackgroundFill(Color.web("#4aa55f"), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (role == ProjectMemberRole.MEMBER) {
            container.setBackground(new Background(new BackgroundFill(Color.web("#80a349"), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (role == ProjectMemberRole.OBSERVER) {
            container.setBackground(new Background(new BackgroundFill(Color.web("#a08d48"), CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    public HBox getContainer() {
        return container;
    }
}
