package com.noter.scenes.dashboard;

import com.noter.Noter;
import com.noter.models.Account;
import com.noter.models.Project;
import com.noter.models.Task;
import com.noter.scenes.dashboard.components.task.TaskView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Willian Gois (github/willgoix)
 */
public class DashboardController implements Initializable {

    @FXML
    private Text usernameText;

    @FXML
    private Text emailText;

    @FXML
    private VBox projectsContainer;

    @FXML
    private VBox tasksContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Account account = Noter.getNoter().getSessionManager().getLoggedAccount();

        loadLabels(account);
        loadProjects();
    }

    private void loadLabels(Account account) {
        usernameText.setText(account.getUsername());
        emailText.setText(account.getEmail());
    }

    private void loadProjects() {
        //HashMap<Project, ProjectMemberRole> projects = Noter.getNoter().getProjectManager().getProjects();

        List<Label> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) list.add(new Label("Item " + i));

        ObservableList<Label> observableList = FXCollections.observableList(list);

        projectsContainer.getChildren().setAll(observableList);
    }

    public void loadTasks(Project project) {
        tasksContainer.getChildren().clear();

        for (Task task : project.getTasks()) {
            Date createdAt = new Date(task.getCreatedAt());
            String createdAtDisplay = createdAt.toLocaleString();

            TaskView taskView = new TaskView();
            taskView.setTaskName(task.getName());
            taskView.setTaskDateCreation(createdAtDisplay);

            tasksContainer.getChildren().add(taskView);
        }
    }

    @FXML
    public void handleConfigurations() {

    }
}