package com.noter.scenes.dashboard.components.project;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * @author Willian Gois (github/willgoix)
 */
public class ProjectView extends AnchorPane {

    @FXML
    private Text projectName;

    public ProjectView() {
        super();
    }

    public void setProjectName(String name) {
        projectNameProperty().set(name);
    }

    public String getTProjectName() {
        return projectNameProperty().get();
    }

    public StringProperty projectNameProperty() {
        return projectName.textProperty();
    }
}
