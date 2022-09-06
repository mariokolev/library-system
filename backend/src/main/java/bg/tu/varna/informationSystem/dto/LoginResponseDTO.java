package bg.tu.varna.informationSystem.dto;

import java.util.List;

public class LoginResponseDTO {

    private Long id;

    private String username;
    private String token;
    private List<String> permissions;
    private String role;

    private Long libraryId;

    public LoginResponseDTO(Long id, String username, String token, List<String> permissions, String role, Long libraryId) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.permissions = permissions;
        this.role = role;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Long libraryId) {
        this.libraryId = libraryId;
    }
}
