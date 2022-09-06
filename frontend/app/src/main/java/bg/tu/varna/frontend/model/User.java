package bg.tu.varna.frontend.model;

import java.util.List;

public class User {
    private Long id;
    private String username;
    private String role;
    private List<String> permissions;
    private String token;
    private Long libraryId;

    public User() {

    }

    public User(Long id, String username, String role, List<String> permissions, String token, Long libraryId) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.permissions = permissions;
        this.token = token;
        this.libraryId = libraryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Long libraryId) {
        this.libraryId = libraryId;
    }
}
