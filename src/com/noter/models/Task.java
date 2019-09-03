package com.noter.models;

/**
 * @author Willian Gois (github/willgoix)
 */
public class Task {

    private int id;
    private String name;
    private String description;
    private long createdAt;
    private boolean completed;

    public Task(int id, String name, String description, long createdAt, boolean completed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.completed = completed;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public boolean isCompleted() {
        return completed;
    }
}
