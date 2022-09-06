package bg.tu.varna.frontend.network.model;

import java.util.List;

public class UserRequestDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String roleName;
    private List<Long> libraryIds;

    public UserRequestDto() {

    }

    public UserRequestDto(String username, String password, String firstName, String lastName, String roleName, List<Long> libraryIds) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleName = roleName;
        this.libraryIds = libraryIds;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Long> getLibraryIds() {
        return libraryIds;
    }

    public void setLibraryIds(List<Long> libraryIds) {
        this.libraryIds = libraryIds;
    }
}
