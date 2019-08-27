package com.noter.models;

/**
 * @author Willian Gois (github/willgoix)
 */
public enum ProjectMemberRole {

    AUTHOR("Criador - pode ver, editar, criar e apagar"),
    MEMBER("Membro - pode ver e editar"),
    OBSERVER("Observador - pode ver");

    private String name;

    ProjectMemberRole(String name) {
        this.name = name;
    }

    public int getID() {
        return ordinal();
    }

    public String getName() {
        return name;
    }

    public boolean higherOrEqualThan(ProjectMemberRole role) {
        return getID() <= role.getID();
    }

    public static ProjectMemberRole getRoleById(int id) {
        for (ProjectMemberRole role : values()) {
            if (role.getID() == id) {
                return role;
            }
        }
        return null;
    }
}
