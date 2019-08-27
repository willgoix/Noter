package com.noter.manager.impl;

import com.noter.Noter;
import com.noter.models.Account;
import com.noter.models.Project;
import com.noter.models.ProjectMemberRole;
import com.noter.storage.NoterStorage;
import com.noter.storage.ProjectStorage;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Willian Gois (github/willgoix)
 */
public class ProjectManager {

    private final ProjectStorage storage;

    private HashMap<Project, ProjectMemberRole> projects;

    public ProjectManager(NoterStorage noterStorage) {
        this.storage = new ProjectStorage(noterStorage);
        this.projects = new HashMap<>();
    }

    public void start(Account account) {
        projects.putAll(storage.getProjectsByAccount(account));
    }

    public void addProject(Project project) {
        projects.put(project, ProjectMemberRole.AUTHOR);
        storage.addProject(project, Noter.getNoter().getSessionManager().getLoggedAccount(), ProjectMemberRole.AUTHOR);
    }

    public void removeProject(Project project) {
        projects.remove(project);
        storage.removeProject(project);
    }

    public List<Project> getAuthoredProjects() {
        return projects.entrySet().stream().filter(entry -> entry.getValue().higherOrEqualThan(ProjectMemberRole.AUTHOR)).map(entry -> entry.getKey()).collect(Collectors.toList());
    }

    public HashMap<Project, ProjectMemberRole> getProjects() {
        return projects;
    }
}
