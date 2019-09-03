package com.noter.storage;

import com.noter.models.Account;
import com.noter.models.Project;
import com.noter.models.ProjectMemberRole;
import com.noter.models.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Willian Gois (github/willgoix)
 */
public class ProjectStorage {

    private final NoterStorage storage;

    public ProjectStorage(NoterStorage storage) {
        this.storage = storage;

        storage.query(
                "CREATE TABLE IF NOT EXISTS " + storage.TABLE_PROJECTS + "("
                + "id INT PRIMARY KEY, "
                + "name VARCHAR(32) NOT NULL, "
                + "description VARCHAR(100));");
        storage.query("CREATE TABLE IF NOT EXISTS " + storage.TABLE_PROJECT_MEMBERS + "("
                + "project INT NOT NULL, "
                + "member VARCHAR(16) NOT NULL, "
                + "role CHAR(1) NOT NULL, "
                + "FOREIGN KEY (project) REFERENCES projects(id), "
                + "FOREIGN KEY (member) REFERENCES accounts(username));");
        storage.query("CREATE TABLE IF NOT EXISTS " + storage.TABLE_TASKS + "("
                + "id INT PRIMARY KEY, "
                + "name VARCHAR(50) NOT NULL, "
                + "description VARCHAR(100), "
                + "created_at LONG NOT NULL, "
                + "completed BOOLEAN NOT NULL, "
                + "project INT,"
                + "FOREIGN KEY (project) REFERENCES projects(id));");
        storage.query("CREATE TABLE IF NOT EXISTS " + storage.TABLE_TASKS_MEDIAS + "("
                + "task INT, "
                + "mediaUrl VARCHAR(255), "
                + "FOREIGN KEY (task) REFERENCES tasks(id));");
    }

    public void addProject(Project project, Account member, ProjectMemberRole role) {
        storage.query("INSERT INTO "+ storage.TABLE_PROJECTS + " (id, name, description) VALUES (?, ?, ?);",
                project.getID(),
                project.getName(),
                project.getDescription());
        storage.query("INSERT INTO "+ storage.TABLE_PROJECT_MEMBERS + " (project, member, role) VALUES (?, ?, ?);",
                project.getID(),
                member.getUsername(),
                role.getID());
    }

    public void addTask(Project project, Task task) {
        storage.query("INSERT INTO "+ storage.TABLE_TASKS + " VALUES (?, ?, ?, ?, ?, ?);",
                task.getID(),
                task.getName(),
                task.getDescription(),
                task.getCreatedAt(),
                task.isCompleted(),
                project.getID());
    }

    public void updateProject(Project project) {
        storage.query("UPDATE TABLE " + storage.TABLE_PROJECTS + " SET name = ?, description = ? WHERE id = ?;",
                project.getName(),
                project.getDescription(),
                project.getID());
    }

    public void removeProject(Project project) {
        storage.query("DELETE FROM " + storage.TABLE_PROJECTS + " WHERE id = ?;" +
                        "DELETE FROM " + storage.TABLE_PROJECT_MEMBERS + " WHERE project = ?;",
                project.getID(),
                project.getID());
    }

    public Map<Project, ProjectMemberRole> getProjectsByAccount(Account account) {
        HashMap<Project, ProjectMemberRole> projects = new HashMap<>();
        Map<Integer, HashMap<Account, ProjectMemberRole>> projectMembers = new HashMap<>();

        /* relacionando todos os projetos do usuário "account.getUsername()" e os membros dos mesmos.*/
        String query = "SELECT a.*, "+ storage.TABLE_PROJECTS +".*, b.role FROM " +
                "" + storage.TABLE_ACCOUNTS + " a "+
                "LEFT JOIN "+ storage.TABLE_PROJECT_MEMBERS +" b ON(? = b.member) " +
                "LEFT JOIN "+ storage.TABLE_PROJECTS +" ON(id = project)" +
                "LEFT JOIN "+ storage.TABLE_PROJECT_MEMBERS +" c ON(id = c.project) " +
                "LEFT JOIN "+ storage.TABLE_ACCOUNTS +" membros ON(c.member = membros.username);";

        try (PreparedStatement statement = storage.getConnection().prepareStatement(query)) {
            statement.setString(1, account.getUsername());

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Account member = new Account(
                        result.getString("username"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getString("passwordSalt")
                );
                ProjectMemberRole role = ProjectMemberRole.getRoleById(result.getInt("role"));
                int projectId = result.getInt("id");

                if (projectMembers.containsKey(projectId)) {
                    projectMembers.get(projectId).put(member, role);
                } else {
                    HashMap<Account, ProjectMemberRole> map = new HashMap<>();
                    map.put(member, role);
                    projectMembers.put(projectId, map);

                    if (member.getUsername().equals(account.getUsername())) {
                        Project project = new Project(
                                result.getInt("id"),
                                result.getString("name"),
                                result.getString("description"),
                                account,
                                new ArrayList<>(),
                                new HashMap<>()
                        );

                        projects.put(project, role);
                    }
                }
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        projects.forEach((project, accountRole) -> {
            projectMembers.forEach((projectId, members) -> {
                if (project.getID() == projectId) {
                    members.forEach((member, role) -> {
                        project.getMembersAndRoles().put(member, role);
                    });
                }
            });
        });

        return projects;
    }
}
