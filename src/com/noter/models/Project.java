package com.noter.models;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Willian Gois (github/willgoix)
 */
public class Project {

    private int id;
    private String name;
    private String description;
    private Account author;
    private List<Task> tasks;
    private Map<Account, ProjectMemberRole> members;

    public Project(int id, String name, String description, Account author, Map<Account, ProjectMemberRole> members) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.members = members;
    }

    public int getID() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Account getAuthor() {
        return author;
    }

    public void addMember(Account account, ProjectMemberRole role) {
        members.put(account, role);
    }

    public void removeMember(Account account) {
        members.remove(account);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Set<Account> getMembers() {
        return members.keySet();
    }

    public Map<Account, ProjectMemberRole> getMembersAndRoles() {
        return members;
    }

    @Override
    public boolean equals(Object object) {
        Project project = (Project) object;

        return this.id == project.getID();
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Project [" + "id = " + id + "name = " + name + "description = " + description + "]";
    }
}
