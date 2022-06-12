package bg.tu.varna.informationSystem.dto;

import java.util.List;

public class LoginResponseDTO {

    private String token;
    private List<String> permissions;
    private String role;

    public LoginResponseDTO(String token, List<String> permissions, String role) {
        this.token = token;
        this.permissions = permissions;
        this.role = role;
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
}
