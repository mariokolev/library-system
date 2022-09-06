package bg.tu.varna.frontend.utils.common;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum RoleType {
    READER("reader"),
    OPERATOR("operator"),
    ADMIN("admin");

    public final String role;

    RoleType(String role) {
        this.role = role;
    }

    public static List<String> getRoles() {
        return Arrays.stream(RoleType.values()).map(role-> role.role).collect(Collectors.toList());
    }

    public static RoleType getValue(String value) {
        switch (value) {
            case "reader":
                return READER;
            case "operator":
                return OPERATOR;
            case "admin":
                return ADMIN;
            default:
                throw new IllegalStateException("Невалидна роля.");
        }
    }
}
