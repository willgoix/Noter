package com.noter.storage;

import com.noter.models.Account;
import com.noter.models.Project;
import com.noter.models.ProjectMemberRole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Willian Gois (github/willgoix)
 */
public class ProjectStorage {

    private final NoterStorage storage;

    public ProjectStorage(NoterStorage storage) {
        this.storage = storage;

        /*storage.query(
                "CREATE TABLE IF NOT EXISTS " + storage.TABLE_PROJECTS + "("
                + "id INT PRIMARY KEY, "
                + "name VARCHAR(32) NOT NULL, "
                + "description VARCHAR(100), "
                //+ "author VARCHAR(16),"
                + "FOREIGN KEY (author) REFERENCES accounts(username));"
                + " "
                + "CREATE TABLE IF NOT EXISTS " + storage.TABLE_PROJECT_MEMBERS + "("
                + "taskDetails INT NOT NULL, "
                + "member VARCHAR(16) NOT NULL, "
                + "role CHAR(1) NOT NULL, "
                + "FOREIGN KEY (taskDetails) REFERENCES projects(id), "
                + "FOREIGN KEY (member) REFERENCES accounts(username));"
        );*/
    }

    public void addProject(Project project, Account member, ProjectMemberRole role) {
        storage.query("INSERT INTO "+ storage.TABLE_PROJECTS + " (id, name, description) VALUES (?, ?, ?);" +
                        "INSERT INTO "+ storage.TABLE_PROJECT_MEMBERS + " (taskDetails, member, role) VALUES (?, ?, ?);",
                project.getID(),
                project.getName(),
                project.getDescription(),
                project.getID(),
                member.getUsername(),
                role);
    }

    public void updateProject(Project project) {
        storage.query("UPDATE TABLE " + storage.TABLE_PROJECTS + " SET name = ?, description = ? WHERE id = ?;",
                project.getName(),
                project.getDescription(),
                project.getID());
    }

    public void removeProject(Project project) {
        storage.query("DELETE FROM " + storage.TABLE_PROJECTS + " WHERE id = ?;" +
                        "DELETE FROM " + storage.TABLE_PROJECT_MEMBERS + " WHERE taskDetails = ?;",
                project.getID(),
                project.getID());
    }

    public Map<Project, ProjectMemberRole> getProjectsByAccount(Account account) {
        HashMap<Project, ProjectMemberRole> projects = new HashMap<>();

        String query = "SELECT "+ storage.TABLE_PROJECT_MEMBERS +".member, "+ storage.TABLE_PROJECT_MEMBERS +".role, "+ storage.TABLE_PROJECTS +".* FROM "+ storage.TABLE_ACCOUNTS +" LEFT JOIN "+ storage.TABLE_PROJECT_MEMBERS +" ON(member=username) LEFT JOIN "+ storage.TABLE_PROJECTS +" ON(id = taskDetails);";
        try (PreparedStatement statement = storage.getConnection().prepareStatement(query)) {
            //statement.setString(1, account.getUsername());

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Map<Account, ProjectMemberRole> members = new HashMap<>();


                Project project = new Project(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("description"),
                        account,
                        null //TODO: Get members.
                );
                ProjectMemberRole role = ProjectMemberRole.getRoleById(result.getInt("role"));

                projects.put(project, role);
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }
}
