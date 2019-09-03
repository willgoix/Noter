package com.noter.scenes.dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.noter.Noter;
import com.noter.models.Account;
import com.noter.models.Project;
import com.noter.models.ProjectMemberRole;
import com.noter.models.Task;
import com.noter.scenes.dashboard.components.project.ProjectView;
import com.noter.scenes.dashboard.components.task.TaskView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
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

    @FXML
    private SplitPane splitableTasksContainer;

    @FXML
    private TextField newTaskName;

    @FXML
    private JFXButton addTaskButton;

    private ObjectProperty<Project> selectedProject = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //addTaskButton.disableProperty().bind(selectedProject.isNull());

        /* loading */
        Account account = Noter.getNoter().getSessionManager().getLoggedAccount();

        loadLabels(account);
        loadProjects();
    }

    private void loadLabels(Account account) {
        usernameText.setText(account.getUsername());
        emailText.setText(account.getEmail());
    }

    private void loadProjects() {
        HashMap<Project, ProjectMemberRole> projects = Noter.getNoter().getProjectManager().getProjects();

        projectsContainer.getChildren().clear();

        for (Entry<Project, ProjectMemberRole> entry : projects.entrySet()) {
            Project project = entry.getKey();
            ProjectMemberRole role = entry.getValue();

            addProjectView(project, role);
        }

        if (!projects.isEmpty()) {
            loadTasks(projects.keySet().iterator().next());
        }
    }

    private void loadTasks(Project project) {
        tasksContainer.getChildren().clear();

        for (Task task : project.getTasks()) {
            addTaskView(task);
        }
    }

    private void addProjectView(Project project, ProjectMemberRole role) {
        ProjectView projectView = new ProjectView();
        projectView.setProjectName(project.getName());
        projectView.setAccountRole(role);
        projectView.setOnMouseClicked(event -> {
            selectedProject = new SimpleObjectProperty<>(project);
            loadTasks(project);
        });

        projectsContainer.getChildren().add(projectView);
    }

    private void addTaskView(Task task) {
        Date createdAt = new Date(task.getCreatedAt());
        String createdAtDisplay = createdAt.toLocaleString();

        TaskView taskView = new TaskView(splitableTasksContainer);
        taskView.setTaskName(task.getName());
        taskView.setTaskDateCreation(task.getDescription() == null ? "Sem descrição" : task.getDescription());
        taskView.setTaskDateCreation(createdAtDisplay);

        tasksContainer.getChildren().add(taskView);
    }

    @FXML
    public void handleConfigurations() {

    }

    @FXML
    public void handleAddTask() {
        if (newTaskName.getText() == null || newTaskName.getText().isEmpty()) {
            return;
        }
        Task task = new Task(new Random().nextInt(10000), newTaskName.getText(), null, System.currentTimeMillis(), false);
        addTaskView(task);
        Noter.getNoter().getProjectManager().addTask(selectedProject.get(), task);
    }

    @FXML
    public void handleAddProject() {
        for (Node node : projectsContainer.getChildren()) {
            if (node.getId() != null && node.getId().equals("tempInput")) projectsContainer.getChildren().remove(node);
        }

        ProjectView projectView = new ProjectView();
        Label nameLabel = (Label) projectView.getContainer().getChildren().get(ProjectView.NAME_NODE_INDEX);

        JFXTextField nameInput = new JFXTextField();
        nameInput.setId("tempInput");
        nameInput.setPrefWidth(160);
        nameInput.setPrefHeight(30);
        nameInput.setPromptText("Nome do projeto...");
        nameInput.setUnFocusColor(Color.web("#13383d"));
        nameInput.setFocusColor(Color.web("#4aa55f"));
        nameInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                /* Cancel project creation */
                projectView.getChildren().remove(projectView);
            }
        });
        nameInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                /* Add new project */
                nameLabel.setText(nameInput.getText());
                projectView.getContainer().getChildren().remove(ProjectView.NAME_NODE_INDEX);

                Project project = new Project(nameInput.getText(), Noter.getNoter().getSessionManager().getLoggedAccount());
                projectsContainer.getChildren().remove(projectView);
                addProjectView(project, ProjectMemberRole.AUTHOR);
                Noter.getNoter().getProjectManager().addProject(project);
                selectedProject = new SimpleObjectProperty<>(project);
            } else if (event.getCode() == KeyCode.ESCAPE) {
                /* Cancel project creation */
                projectsContainer.getChildren().remove(projectView);
            }
        });
        projectView.getContainer().getChildren().set(ProjectView.NAME_NODE_INDEX, nameInput);

        System.out.println(projectView.getScaleX());
        projectsContainer.getChildren().add(projectView);
        System.out.println(projectView.getScaleX());
        nameInput.requestFocus();
    }

    @FXML
    public void handleMinimize() {

    }

    @FXML
    public void handleMaximize() {

    }

    @FXML
    public void handleClose() {
        System.exit(0);
    }
}