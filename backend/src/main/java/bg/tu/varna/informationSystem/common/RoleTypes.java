package bg.tu.varna.informationSystem.common;

public enum RoleTypes {
    READER,
    OPERATOR,
    ADMIN;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
